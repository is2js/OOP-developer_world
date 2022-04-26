### 기본 명세

![image-20220426214804099](https://raw.githubusercontent.com/is2js/screenshots/main/image-20220426214804099.png)

- Director
    - Paper들을 DB처럼 HashMap에 String으로 매핑해놓음.
    - runProject()로 Programmer에게 Paper를 던져서 일을 시키는 관리자
        - run메서드가 숨어있는 곳
- Programmer
    1. FrontEnd
    2. BackEnd
- Paper
    1. Client
    2. ServerClient

### 문제 상황

- 각 Programmer 구상체들(프론트, 백)은 각자의 구상체 명세Paper(프-Client, 백-ServerClient)에 맞게 정보를 빼내와야한다.
- 각 Programmer 들은 파라미터에서 추상체 Paper를 받았지만
    - 전략패턴 -> 전략메서드()호출로 인해 자동으로 필요 로직이 실행되는 게 아니라
    - **추상체 -> 특정 구상체로 타입확인(if instanceof) -> 특정 구상체로 타입변환(다운캐스팅, OCP위반)해야하는 상황이다.**
        - 다운캐스팅 -> 확장을 못하게 된다
        - if를 유지 -> **goto를 일으켜 컴파일에러, 런타임에러를 발생시키지 않아, 문제해결이 힘들어진다.**

### 문제 해결방법

- if instance를 제거하여, 그 분기에 대한 내용을, 외부 클라이언트 쪽으로 넘겨야한다.
