## 작동 방식
클라이언트가 비디오를 cloud storage에 업로드하고, firestore 데이터베이스의 `verify-queue` collection에 document를 추가해야 한다.

document에는 `video_url`이라는 필드에 업로드한 비디오의 경로가 적혀있어야 한다.
test.mp4를 cloud storage에 videos/test.mp4로 업로드하면, video_url은 `videos/test.mp4`이다.

서버에서 처리한 후에 document의 `is_processed` 필드가 `True`로 바뀌고 `is_valid` 값에 따라 Deepfake여부를 판단하면 된다.
만약 에러가 발생하면 `is_processed`는 true이면서 `error` 필드에 에러 메시지가 들어간다.

설명에 대한 실제 예제로 실제 Firebase에 남아있는 데이터베이스 내용을 참고하면 된다.

## 서버로만 테스트
서버로만 테스트하고 싶은 겨웅엔 http://localhost:5000/test-add를 호출해서 테스트 가능
아래와 같이 클라이언트에서 데이터 넣는 것을 재현함. 비디오는 미리 업로드되어 있다.
queue.add({"video_url": "videos/real_mandoo.mp4"})

스크립트: test.sh

## 실행하기
./run

## 프로젝트 폴더를 지우고 VM에 다시 설치한 경우 (~/mandoo-ml-api 폴더에 있음)

### poetry (python 패키지 관리)
실행하기 위해 초기화 (vm에선 이미 되어있음, poetry package가 필요함)
cd project_directory
python3.6 -m poetry init
python3.6 -m poetry install

### 파일 넣기
DeepFake_Xception/log_path/에 용량 커서 제외한 체크포인트 파일 넣기
firebase service account json파일을 serviceAccount.json으로 프로젝트 루트에 넣기

### 실행하기
./run

## 로컬 환경에서 pytorch때문에 테스트가 실패한 경우
pytorch 사용하는 부분을 주석 처리하자. 귀찮아서 로컬에선 안하는게 좋음