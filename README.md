# FinTect
*비대면 카드나 계좌 개설과 같은 금융 업무 진행 시의 화상 인증과정에서
딥페이크 탐지 기술을 활용하여 보다 안전하고 정확한 인증을 대행하는 앱 서비스*

<br>
<br>

CS496 Special Topics in Computer Science<Implementation of Deepfake Generation Technology and Service Model through the Android App> [전산학특강<딥페이크 생성 기술 구현과 이를 이용한 안드로이드 앱 서비스 모델 구현>]

:DeepFake Detection Android App <강민주 이의천 심효진>


## System Overview 

 Frontend: Android (with Kotlin)
 Backend:Flask,Firebase
 Deep Learning: Pytorch (Model: DeepFake_Xception)

PyTorch로 머신러닝 모델을 실행하고, Cloud Storage for Firebase를 통해 요청을 queue형 태로 받아서 처리한다. Flask는 모델의 테스트 자동화를 위한 HTTP 요청처리 (test-add)를 위해 활용하였다.

서버 운영 주체: KCLOUD 
사양: vCPU(2) - MEM(8) - DISK(100), GeForce TITAN-X가 1장씩 장착된 VM.





