# HTML, HTTP API, CSR, SSR

### 정적 리소스
- 고정된 HTML파일, CSS, JS, 이미지, 영상 등 제공
- 주로 웹 브라우저들이 요청
### HTML 페이지
- 동적으로 필요한 HTML 파일 생성해서 전달
- 웹브라우저가 HTML을 해석해서 보여줌
### HTTP API
- HTML이 아니라 데이터를 전달
- 주로 JSON 형식
- 다양한 시스템 연동 (UI 클라이언트, 서버 to 서버 등)
- 데이터만 주고 받고 UI 화면이 필요하면 클라이언트가 별도로 처리

### SSR - 서버 사이드 렌더링
- 서버에서 최종 HTML을 생성해서 클라이언트에 전달
- 정적인 화면에 사용

### CSR - 클라이언트 사이드 랜더링
- HTML 결과를 자바스크립트를 사용해서 웹 브라우저에 동적으로 생성하여 적용
- 동적인 화면에 사용, 웹 환경을 마치 앱처럼 필요한 부분을 변경 할 수 있음