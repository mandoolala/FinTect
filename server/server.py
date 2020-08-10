import os
import sys
from PIL import Image
from cv2 import cv2

from flask import Flask

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from firebase_admin import storage

from test_single import DeepfakeTest
from DeepFake_Xception.xception import Xception

app = Flask(__name__)

cred = credentials.Certificate("serviceAccount.json")
firebase_admin.initialize_app(cred)

db = firestore.client()
queue = db.collection("verify-queue")

video_bucket = storage.bucket("fintect-ff87d.appspot.com")

# Listen to changes of queue
def on_snapshot(collection, changes, time):
    for change in changes:
        # document_data = change.document.to_dict()
        # print("[on_snapshot]", change.type.name, document_data, change.document.id)
        if (change.type.name == "ADDED") and has_processed(change.document):
            validate_doc(change.document)

# Check if a job has already been processed
def has_processed(doc_snapshot):
    document_data = doc_snapshot.to_dict()
    return "is_processed" not in document_data or document_data["is_processed"] == False

# Validate and update document
def validate_doc(doc_snapshot):
    print("validating", doc_snapshot.id)
    document_data = doc_snapshot.to_dict()

    try:
        queue.document(doc_snapshot.id).update(
            {"is_processed": True, "is_valid": validate(document_data["video_url"])}
        )
        print('Validation succeed', doc_snapshot.id)
    except:
        exc_type, value, _ = sys.exc_info()
        queue.document(doc_snapshot.id).update(
            {"is_processed": True, "error": "type: {}, {}".format(exc_type, value)}
        )
        print("Error while processing", doc_snapshot.id, exc_type, value)

# Validate
def validate(video_url):
    print('Downloading video...')
    path = download_video(video_url)
    print('Extracting image...')
    img_path = extract_first_frame(path)
    print('Validating...')
    return validate_image(img_path)

def download_video(video_url):
    blob = video_bucket.blob(video_url)
    path = "./tmp/" + os.path.basename(blob.name)
    blob.download_to_filename(path)
    return path

def extract_first_frame(path):
    vid = cv2.VideoCapture(path)
    _, frame = vid.read()
    cv2.imwrite('./tmp/frame.png', frame)
    vid.release()
    
    # Resize image to 128x128
    im = Image.open('./tmp/frame.png')

    width, height = im.size
    space_ratio = max(width, height) / min(width, height)
    
    # Resize to slightly bigger size for cropping
    im.thumbnail((128 * space_ratio, 128 * space_ratio))

    # crop sides
    width, height = im.size   # Get dimensions

    left = (width - 128)/2
    top = (height - 128)/2
    right = (width + 128)/2
    bottom = (height + 128)/2

    im = im.crop((left, top, right, bottom))


    im.save("./tmp/frame_thumb.png")
    return "./tmp/frame_thumb.png"

def validate_image(image_path):

    tester = DeepfakeTest(model=Xception(), ckpt_dir='DeepFake_Xception/log_path/Xception_trained_model.pth')
    result = tester.test_im(image_path)
    
    if (result[0][0] > 0.9):
        real_percentage = result[0][0]*100
        print("result: {0:3.1f}% REAL!".format(real_percentage))
        return True

    fake_percentage = result[0][1]*100
    print("result: {0:3.1f}% FAKE!".format(fake_percentage))
    return False

watch = queue.on_snapshot(on_snapshot)

@app.route("/test-add")
def test_add():
    # Adding a document to verify-queue will execute a task
    queue.add({"video_url": "videos/real_mandoo.mp4"})
    return "OK"

