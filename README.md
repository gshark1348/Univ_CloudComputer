# Univ_CloudComputer
Java Socket API를 활용하여 클라이언트가 계산 표현(ADD, SUB, MUL, DIV)을 서버로 전송하고, 서버가 이를 passing 및 계산하여 응답하는 ‘cloud calculator’ 애플리케이션을 구성. 응답은 정상, 에러(에러 사유)로 반환되고 서버는 ThreadPool로 다중 클라이언트를 동시에 처리함. 클라이언트는 server_info.dat에서 서버 IP/포트를 읽고 연결함. (파일 미 존재 시 기본값 localhost:1234 를 사용)
