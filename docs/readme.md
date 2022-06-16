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

### 과정

- [base domain 파일 github](https://github.com/LenKIM/object-book/commit/629c078afffdbc76aba3ade318e0b1c004b4cf96#diff-951161e362c78e0593e03eb34335ee8cf6e0a9f3412638d338dd0d63d8f76c25)

1. LSP위반 문제점
	1. 서로 다른 필드를 가지는 `Server`, `ClientServer`의 추상체 `Paper`를 받더라도, 마커인터페이스로서 한번에 일시키는 메소드가 없고 카테고리만 만들어서
		- 이것을 받는 Programmer가 내부에서 **구상체 확인 by isinstanceof**을 해야한다.
		- 부모형인 Paper자체만으로 자식형 Server, ClientServer가 알아서 처리되지 못한다면, **LSP 위반**이다.
		- **구상체 추가시 if가 추가되는 OCP위반**에 선행되는 문제점이 **부모형으로 자식형을 대체 못함(LSP위반)**이다.

