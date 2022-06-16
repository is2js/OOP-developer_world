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

2. LSP(isinstanceof) 해결 시행착오 by 헐리웃 원칙
    - 내 생각: 추상체가 구상체를 대신 못하는 이유는 한꺼번에 일을 못시키기 때문에, 구상체의 지식을 직접 물어보는 것
    - **구상체인지 물어보지말고, `추상체`에게 (메서드 생성)하여 직접 시킨다.(헐리웃원칙)**
        - 재료(필드정보)를 꺼내와서 현재 객체 필드에 set시켜주는 작업을 직접시킨다면, `현재객체(this)`를 건네줘야한다.
            - 근데, 건네 주는 것이 카테고리를 가지는 구상체 중 1개라서 추상체로 건네줘야하고, 구상체마다 가지는 필드가 달라서 또 거기서 구상체확인을 해야하는 LSP위반이 발생한다.(차후)
    - 받은 추상체에서 정보를 꺼내와서(getter) 내(this)필드에 할당(setter)
        - this의 나란 객체를 넘기고 -> data를 set해야함.
        - **get으로 물어보는 것을 시키게 된다면 -> `setXXX()`의 명칭으로 메서드로 시킨다**
    - **`this`를 받는 쪽은 `상위형`으로 받아줘야하고, 어떤 구상형이든 들어갈 수 있게 설계해야한다.**
        - **`인자로 this`를 사용했다? -> 책임을 완전히 위임하여 더이상 `어떤 구상형인지 안물어봐도 된다`.**
        - **추상체로 받기 때문에 `추가 구상형을 확장할 수 있다`**

    
    - **시키다보니, Programmer 역시 추상체로 넘어가서, 구현체마다 서로 다른 필드를 가지고 있기 때문에, 재료정보를 박아주기 위해 생성되는 setter가, 공통이 아닌 필드의 setter까지 인터페이스에 올라가버린다.**
        ![11471d15-0e22-4018-9229-046ec8227f9f](https://raw.githubusercontent.com/is2js/screenshots/main/11471d15-0e22-4018-9229-046ec8227f9f.gif)

        ![20220616160834](https://raw.githubusercontent.com/is2js/screenshots/main/20220616160834.png)
        - 백엔드에서 사용하지 않는 library필드의 setter
            ![20220616160857](https://raw.githubusercontent.com/is2js/screenshots/main/20220616160857.png)
        - 프론트에서 사용하지 않는 server필드의 setter
            ![20220616160935](https://raw.githubusercontent.com/is2js/screenshots/main/20220616160935.png)

    - 일단 공통기능이 아니므로 인터페이스에서는 명세는 삭제하고
        - **공통기능 아님 -> `구현체들 개별적으로 사용`해야하는데, `this -> 추상체`로 넘겨봤던 Paper의 구현체들은 넘겨받은 Programmer(추상체)를 받아서 내부에서 `역으로 instanceof`로 물어봐야한다.**

3. **공통기능이 없는 추상체를 넘겨받으면, LSP위반으로 instanceof로 물어봐서 개별 구상체의 기능을 이용해야한다.**
    - this로 구상체들을 추상체로 넘겨줄지라도, 공통기능이 없는 구상체들은 내부에서 사용될 때 LSP위반하게 되어있다.
        - **`추상층이 포함하는 내용(공통로직)이 일반적으로 적어서` -> 추상층에 정보가 적다 -> `다운캐스팅해서 물어볼 수 밖에 없는게 일반적`이기 때문이다.**
    - **LSP위반 해결책이 헐리웃원칙이지만, 시켜서 넘어가는 놈이 `공통기능 없는 추상체`일 경우, 똑같은 LSP문제가 발생한다.**
        - Client(Paper)
            ![20220616162114](https://raw.githubusercontent.com/is2js/screenshots/main/20220616162114.png)