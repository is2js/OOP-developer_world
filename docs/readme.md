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
        - ServerClient(Paper)
            ![20220616163756](https://raw.githubusercontent.com/is2js/screenshots/main/20220616163756.png)

    - **추상체는 메서드 1개이하여야한다. 그럼에도 불구하고 추상층의 정보가 적지않으려면??**


4. LSP위반과 별개로 공통로직을 가진 구현체들의 추상층을 인터페이스 -> 추상클래스(템플릿메서드패턴=`외부호출용 public 1개` + 그 내부에서 `step별 자식들이 개별구현할 proteced abstract 메서드들`)의 추상클래스로 만들기
    - 공통로직을 추상층에 올리지 않으면 `DRY원칙 위반`이다.
    - 추상클래스도 메서드가 1개이하이면서 `정보를 많이 담은 추상층`으로 만드는 방법이 `템플릿 메소드 패턴`을 이용하는 것이다.
        - 내부에서 protected abstract step메서드들을 자식들이 개별구현한다.
        - **public 템플릿 메소드는 개별 자식들에게는 안보이지만 물려받는다.**
    1. 새롭게 `@Override로 추상클래스를 뽑아내기` 위해서, 상대적으로 가벼운 기존 인터페이스를 삭제한다.
        ![e9f2f1e1-aef6-45fd-a2fa-e20581e48799](https://raw.githubusercontent.com/is2js/screenshots/main/e9f2f1e1-aef6-45fd-a2fa-e20581e48799.gif)
        - **implements 구상체들마다 직접 삭제해야한다**
        ![20220616172755](https://raw.githubusercontent.com/is2js/screenshots/main/20220616172755.png)
    2. 구상체 1개로 `공통 로직을 포함하는 메서드`(to public 템플릿메서드)를 @Override 리팩토링을 통해서 `Extract superclass`로 추출하되
        - 공통로직은 추상클래스에 위치하여 구상체에서는 안보이게 된다.
        - **내부에 딸린 개별구현로직은 `abstract`로 추출한다.**
            - **개별로직은 abstract 메서드가 되어, 자식이 @Override한다.**
            - **다른 구상체들이 @Override할 수 있게 `메서드명 추상화`를 따로 해줘야한다.**
        - **이제 다른 구상체들이 추상클래스를 extends하고, `자신이 가진 개별로직을 훅메서드를 오버라이딩 한 곳으로 옮겨준다`.**
        ![6528a9bb-169b-4ccf-b7b5-e41d9047d5c2](https://raw.githubusercontent.com/is2js/screenshots/main/6528a9bb-169b-4ccf-b7b5-e41d9047d5c2.gif)

        
        
        


5. LSP(instanceof)의 진짜 해결책: 제네릭
    - `추상형`(인터페이스, 추상클래스) -> `if(OCP)`를 해결한다.
        - 형을 통일시켜서 외부에서 그에 맞는 형을 주입하고, 내부에서 if로 판단하지 않게 한다.

    - **`제네릭` -> `instanceof(LSP)`를 해결한다.**
        - `정보가 적은 추상층`을 넘겨주면, 받는 쪽에서는 어느구현체인지 확인할 수 밖에 없는데(LSP위반), 이것을 해결해준다.

    - if(runtime), instanceof(context에러)를 미리 못 잡는데, 제네릭을 이용해서 instanceof를 제거하면 `type(형)`에 의해 에러를 내므로 -> `compile에러`로서 미리 잡을 수 있게 된다.

    
    - **`추상층에서 upperbound(타추상층) T형을 도입 -> 메서드의 인자로 사용`하면 -> 구상층에서는 upperbound의 구현체(타구현체)를 구상층(구현체들)에서 `<T>좁혀서 제네릭 + 메서드인자`로 사용할 수 있다.**
        - **제네릭을 사용하면,  추상층1에서 T extends 추상체2의 T 자리에 구상체2을 지원 -> 구상층1에서 형으로 (특정 구상체를 사용할 수 있게끔) 확 줄임**

    - 제네릭은 기계적으로 사용할 수 있다. 매핑하는 것처럼
        - 현재 클래스의 추상층에 제네릭으로서 upperbound로 T형을 꽂아주고, 추상메소드 정의시 T를 사용해서 정의
        - 이 추상층을 -> 구현이나, 메소드정의시 T자리에 구상형을 직접 입력해서 정의

    1. 추상체(Programmer programmer)를 받아 **instanceof를 사용하는 메서드의 인자**를 `구상체(FrontEnd programmer)`를 직접 사용해서 받게 하되,
        - 메서드의 추상층메서드는 `메서드(T programmer)`로 T형이 오게 한다.
        - 추상층 class자체는 `<T extends Programmer>`로 타추상체의 구현체들만 올 수 있게 제한이 있는 T형 제네릭을 주고
        - 다시 돌아와서 메서드의 class는 `Paper<FrontEnd>`로 주어서, 특정 구상체로 제한한다.
            - 이로 인해, 추상층은 Programmer구현체의 Type만 메서드의 인자로 갈 수 있어서, 추상체인자와 같은 역할을 하게 되며
            - 구상층은, 자기가 확인이 필요한 구현체만 인자로 직접 받아쓸 수 있게 된다.
        - **즉, 1)메서드는 구상체인자 -> 2)추상층메서드는 T형인자 -> 3)추상층class는 T형을 Programmer로 제한하는 제네릭 -> 4) `메서드class는 구상체타입을 제네릭으로 사용하도록 extends 추상체<타구상체>`형식으로 구사한다.**
        ![05ee2fa5-21ee-45c6-a1ec-3946601dec0b](https://raw.githubusercontent.com/is2js/screenshots/main/05ee2fa5-21ee-45c6-a1ec-3946601dec0b.gif)

    2.  **if를 제거하려면, 분기에 해당하는 수만큼 객체를 만들 수 밖에 없고, 선택은 클라코드에 위임**했었다.
        - **`Client가 범용`(FE or BE or 추가될 개발자) 이었다가 메서드인자의 형을 줄이기 위해 `제네릭`을 사용했더니
            -  `if instanceof`제거되었지만
            - 클래스에도 메서드에 사용된 T형을 좁히기 위해 `1개 분기(FE)에 대한 객체(Class)만 생성`되어버렸다.
                ![20220616182550](https://raw.githubusercontent.com/is2js/screenshots/main/20220616182550.png)

            - **필요시 `BE를 받아들이는 Client`도 새롭게 정의해줘야한다.**
        - 넘어왔던 추상체 Programmer에 대해 `구상체 1개인 FE를 전문 담당`하도록 **FE전용  Client class가 정의**되버렸다.
            - **`제네릭을 사용하게 되면 class를 if instanceof case만큼 class를 추가 생성`해하며, 그 선택은 바깥에서 한다.**
            - **제네릭 / 전략패턴 / 인터페이스  ->  다 if를 이런식으로 제거한다.**
            - **`if는 제거되었지만, 그만큼 객체/class가 생성되며 -> 선택은 클라코드`에서 한다**

    3. 그렇다면, instanceof가 2개가 동시에 사용된 class ServerClient는?
        ![20220616182930](https://raw.githubusercontent.com/is2js/screenshots/main/20220616182930.png)
        1. 메서드인자에 구상형을 지정해줄 수없다
        2. 제네릭으로 형을 좁힐 때는, 1개만 적용가능하다.
        3. Paper에 set작업을 시킨 것을 롤백해야한다.
            - Paper를 넘겨받아 물어보는 것은 1가지만 물어보면 됬었다.
            - Paper에게 this(Programmer)를 넘겼떠니 한번에 2가지를 물어보는 1:M관계가 나왔다.
                - **1개의 paper에는 여러개의 programmer작업할 수 있는 관계**
            - **그러나 이상적인 객체관계는 M쪽(Programmer)에서 1쪽(Paper)를 `알게한다` = `의존한다` = `사용한다` = `인자로 넘겨받는다`**
    4. 항상 N쪽에서 1을 알게 하자
        - Many테이블에서 fk를 가지고, One테이블에서는 하위도메인 정보를 모르게 한다.(fk테이블 정보가 없다)
            - sqlalchemy에서는 many테이블에서 fk를 가지면 해당변수는 단수로 편함. one테이블에서 fk정보를 가지면 list로 가져야해서 복잡
        - **1에서 정보를 가진다? 많이 알아야한다 -> 의존성의 무게가 커진다. `항상 many쪽에서 one의 정보를 알자=의존하자=사용하자=인자로 넘겨주자`**

6. paper(1쪽)에 programmer(N쪽)을 넘겨서 시키는 일(paper종류마다 programmer종류마다 field에 set을 위임)을 `롤백`함
    1. `Paper`는 마커인터페이스로 롤백(제네릭 삭제) 
        - 구현체들(`Client`, `ServerClient`)은 data-oriented class로 롤백
        - programmer필드만 set메서드로 채워주는 것만 메서드로 가짐
        ![20220616215556](https://raw.githubusercontent.com/is2js/screenshots/main/20220616215556.png)
        ![20220616220225](https://raw.githubusercontent.com/is2js/screenshots/main/20220616220225.png)
        ![20220616220232](https://raw.githubusercontent.com/is2js/screenshots/main/20220616220232.png)
    2. **`Prgorammer`쪽은 템플릿메소드패턴을 유지하되** paper한테 .setData()의 책임을 `this`로 넘겨서 위임했던 것을
        1. 템플릿메소드내에서 공통로직 && N to 1 위임메서드인 `paper.setData(this)`를 -> 개별구현 훅메서드(abstract protected step메서드들)로 `setData(paper)`로 뺀다.
            - **추상층에서 객체없이 호출**하는 추상메서드는 **구상층에서 구현할 때, 구현체내 필드들을 이용해서 처리하는 메서드**이므로 구상체마다 각자의 필드에 `알아서 처리(abstract)`하도록 위임한다.
        2. 템플릿메소드명을 make`Program`(Paper paper)에서 `getProgram`(Paper paper)로 변경한다.?!
            - 공통로직인 템플릿메소드는, BackEnd든 FrontEnd든 paper에서 재료를 꺼내서 자기필드를 채우고, 프로그램을 만들어서 반환해야한다
                - step1) setData(paper) 공통으로 재료를 박아야함
                    - 필드가 달라 내부구현 다름 `abst step메서드`
                - step2) makeProgram() 공통으로 박은재료들로 프로그램을 만들어줘야함.
                    - 내부구현 다름 `abst step메서드`
        2. Programmer 구현체들(BackEnd, FrontEnd)에서는 setData(paper)를 개별 구현시 `instanceof`로 일단 구현하도록 롤백한다.
            ![20220616223622](https://raw.githubusercontent.com/is2js/screenshots/main/20220616223622.png)
            ![20220616223638](https://raw.githubusercontent.com/is2js/screenshots/main/20220616223638.png)
            ![20220616223655](https://raw.githubusercontent.com/is2js/screenshots/main/20220616223655.png)

    3. 템플릿메소드내에서도 보이지 않는 부모-자식간의 헐리웃원칙이 적용된다.
        - setData(paper)로 자식에게 시켜야하지, 부모(추상클래스내 템플릿메소드 내부)가 뭘 받아서 처리하면 안된다.
        - 객체없이 호출하는 메서드 = this내 필드/메서드이용 메서드
        - 객체 없이 호출하는 추상메서드 = **자식의 필드/메서드 이용해서 구현하는 메서드**
            - 자식이 알아서 get으로 받아오지말고 시킨다.
        

7. Programmer쪽(N에서 1을 받아 구현체확인하여처리)의 instanceof(LSP위반)을 제네릭으로 처리
    1. BackEnd -> FrontEnd순으로 instanceof를 제네릭으로 처리
        - 추상층에서는 Paper의 구상체 아무거나 받아 인자로 쓸 수 있게  T extends Paper의 T형을 받을 수 있게 해주고
        - **각 구상층들은 T형을 1개의 구상체로서 받아서 사용할 수 있게 한다. -> `딱 그 구상층만 받을 수 있는 범용->특정구상체용 class가 된다.`**
            - if를 인터페이스로 줄이면 그 수만큼 구상체 객체class가 생기듯이
            -**if instanceof를 제네릭으로 줄이면 `그 수만큼 개별class가 한정되서 생겨야한다`**
                - 그래서 instanceof는 1개만 가져야한다?!
        ![fa102321-dca5-4933-b3b1-914e65880a59](https://raw.githubusercontent.com/is2js/screenshots/main/fa102321-dca5-4933-b3b1-914e65880a59.gif)

    2. **여기서 `Director`쪽에 어느 paper를 받건 instancof로 `FrontEnd를 범용으로 사용하던 코드`에서 문제가 발생한다.**
        - Director는 1개의 paper를 받아서
            - frontEnd, backEnd 2명에게 다 프로그램만들라 시킨다.
            - 1개의 구상체 paper가 BackEnd용 ServerClient paper라면 -> FrontEnd는 `제네릭으로 인해 1개의 paper만 처리`하도록 = 현재 T타입 중 ServerClient paper전용으로 처리하도록 되어있어서 없던 compile오류(형으로 판단하는 제네릭)가 발생한다
        - my) FrontEnd class가, 특정 추상체에 대해 특정 구상체만 받도록 하기 위해, 
            - `FrontEnd의 추상층`에 특정 추상체를 upperbound T `제네릭` + 메서드에 T
            - FrontEnd class자체를 `특정 구상체 형만 받도록 제한하는 제네릭`을 통해 메서드인자에 특정 구상체만 한정해서 처리할 수있게 함.
            - **하지만, 더 client쪽인 Director내부에서는 FrontEnd가 범용으로 사용되길 원함**
                - my) client쪽에서 특정구상체를 선택할 수있게 FrontEnd<`ServerClient`>의 제네릭을 특정하지말고 또한번 추상화?!
    
        - 객체들을 추상화한 class는 여러 객체들을 변수로 받아 생성할 수 있다. 하지만, **class + `제네릭 개입` -> 제약 = `인자나 필드형에 특정형만 쓸 수 있는` class**
            - 제약된 만큼 제약을 푸려면 `타 추상체의 if instanceof 분기`만큼 class를 따로 생성해야한다.
            - **`자신의 추상층T extends upperbound -> 자신은 구현하며 특정형`의 제네릭이 걸린 class는 어떻게 upperbound의 구상체 수만큼 class를 따로 생성해줄까?**
                - 일반적으로 생성하면, class명이 동일해서 따로 못 만든다.



8. `제네릭에 의해 제약걸린` Programmer `구상체들`을 다시 한번 `추상화를 통해 범용으로` 변환후 `외부로 밀어내기` by 제네릭 제약을 추상화 -> 클라방향으로 구현과 선택 책임을 미룸 with 익명클래스
    - my) instanceof -> 제약을 줄 때도 제네릭 / 제약을 풀 때도 추상화 + 외부에 쓸 제네릭 추가?!
    - instanceof를 안쓰려고 특정형만 받을 수 있게 해주던 `추상층엔 <T extends upperbound>제네릭` + `구상층엔 <특정형>제네릭`을 사용했지만
    - 이번에는 `if제거시 1) 인터페이스로 추상화 + 2) 그만큼 class 생성 + 3) 외부에서 선택`했던 것 처럼
    - **`제네릭으로 제약이 걸린 구상체 class를 = if case 1개`로 보고, 다양한 형을 받을 수 있게 `1) FrontEnd class를 자체를 추상class로 & 특정형을 T로 & 개별구현FrontEnd들이 T를 제네릭으로서 선택할 수있게 FrontEnd<T extends Paper> 추가 & T에 따라 달라지는 메서드도 추상method로 추상화` + `2) T수만큼 class생성 대신 외부에서 익명클래스로 구현 미룸` + `3) 외부에서 <T특정형>과 {추상method 구현} 선택`하는 형태로 바꿔보려고 한다.**
        - Programmer상속으로 인한 `extends Programmer <T>`는 특정형을 T로 선택하겠다는 것이다. 그것과 별개로 `FrontEnd<T extends Paper>`를 따로 줘야 `FrontEnd 구현클래스생성시 제네릭<>형태로 T타입을 개별선택`할 수 있게 된다.

    1. 일단 형 제약된 FrontEnd의 일반class를 
        1. 일반클래스를 `추상클래스로 추상화`
        2. 추상층으로부터 구상층에서 특정형을 선택하는 제네릭 `extends Programmer <특정형>`을 `<T>로 추상화`
        3. 추상클래스가 되면, 외부에서 익명클래스로 구현할 FrontEnd들이, 객체생성시, 제네릭으로 형을 선택할 수 있게 `FrontEnd<T extends Paper>`로 선택할 수 있는 T 제네릭 추가
        4. T선택(Client vs ServerClient)에 따라 구현이 달라지는 `메서드(setData(특정 paper))을 추상메서드화` + `인자`도 (T paper)로 추상화
            - **setter를 외부로 옮긴다면, private필드에 바로 setter가 불가능하니, 추상클래스의 필드들도 익명클래스 내부에서 set할 수 있게 `private -> protecetd`**

    2. 필요한만큼 객체를 못만드는 추상클래스를 `외부에서 익명클래스로 구현`하여 선택
        - `new FrontEnd<>();` 불가 -> **`new FrontEnd<>{ 추상메서드 구현};`로 추상메서드 구현으로 추상클래스를 구현 -> `인스턴스(Programmer frontEnd = )`로 받아 사용가능**
        - if제거시에는 그 수만큼 class 구현(생성) -> 외부에서 선택
            - cf) instanceof 제거시에는 제네릭으로 제약
        - **제약된제네릭 제거시에는 `필요한만큼 외부에서 선택하여 익명class로 구현`**
            - **`T paper별 BackEnd마다 서로 다르게 구현`해야하는 내용이므로  `클라 코드에 위임`시키기 위해 `BackEnd<T>`가 `추상클래스`가 된 것이다.** 
            - **아까는 Prgrammer -> Paper로 밀어냈는데, `클라코드(Main)방향으로 밀어낸 것이 아니라서 문제가 해결X`(협력관계 도메인 레이어(같은 책임 레이어)에서 문제를 떠넘길뿐이었음)**
        
            - 책임을 위임할 땐, `바깥방향, 클라방향으로 시켜서` 밀어내자.
                - `자기보다 클라방향으로 밀어내야지 LSP, OCP위반을 해결`할 수 있다.
        ![6c7eef55-5d3f-46d4-9e25-b3b10e1aadbe](https://raw.githubusercontent.com/is2js/screenshots/main/6c7eef55-5d3f-46d4-9e25-b3b10e1aadbe.gif)

        - 백엔드도 동일하게 수정한다.
            - 제약걸린 class -> 추상화 -> 외부에서 익명클래스로 T형 갯수만큼 구현
        ![1d6cc38b-a8bc-4ec7-9393-e6b6836967e8](https://raw.githubusercontent.com/is2js/screenshots/main/1d6cc38b-a8bc-4ec7-9393-e6b6836967e8.gif)

    3. 제약이 걸려서 에러가 났던 Director내부 Programmer의 템플릿메소드(public, 추상클래스안에 공통로직 구현한 일반 메소드)인  `frontEnd.getProgram(project);`에 에러가 사라졌다.
        - 내부 setData(T paper)를 개별구현한 자식 FrontEnd가 Paper의 특정구상체(Client)만 받던 것을 익명클래스로 구현 + T형 중 1개를 골라서 받을 수 있게 되었기 때문이다.
        ![20220617120233](https://raw.githubusercontent.com/is2js/screenshots/main/20220617120233.png)

    

- 생각 정리하기
    - 객체생성같아보이지만, `{}`로 구현한다면, 객체 생성불가한 `추상클래스를 익명클래스로 구현 -> 객체생성`하는 장면이다.
        ![20220617120815](https://raw.githubusercontent.com/is2js/screenshots/main/20220617120815.png)
    - new Class<특정형>();으로 `외부에서 제네릭을 <특정형>`으로 확정지었다면, `내부에서는 범용T(or upperbound내 범용T)으로 정의`해놓고, `외부에서 상황에 따른 특정형만 받아 처리할 수 있게 선택`하여 특정형을 인자/필드 등으로 쓰는 class로 제약을 거는 것이다.
        - 특정형에 따라 구현이 다르다면, 그에 따른 추상화->구체형class들 갯수만큼 생성이 싫다면, 추상클래스 & 추상메서드로 정의해놓고 -> 외부에서 익명클래스로 구현해준다.
        ![20220617121114](https://raw.githubusercontent.com/is2js/screenshots/main/20220617121114.png)

    - `추상층의 제네릭`은 **구상체마다 `추상형 인자`에 대한 instanceof 1개만 존재할 때, `추상체의 특정구상체 인자`만 아는 class로 제약을 걸어 instaceof를 제거할 수 있게 도와준다.** 
    - `제네릭T화 + 추상클래스`은 제약이 걸려 1개type만 아는 class를 `여러 type을 알 수 있게끔` 다시 T로 추상화할 수 있고, 원하는/알고싶은 특정형의 갯수만큼 `1개의 class를 원하는 만큼의 익명클래스`로 구현할 수 있다.


9. [생각정리] Director의 runProject 속 **instanceof는 2개라서 제네릭으로 제거못한다**. 제네릭은 1개의 class가 1개의 특정형을 알게 해주는 특징을 가짐.
    - instanceof가 2개 = 1:N에서 1에 N의정보를 넘겨 복잡해짐
        - **제네릭 사용 불가로서, 의존성 역전시켜 N(Paper)에 1(Director)를 넘겨 처리하는 식으로 개선해야한다.**
    - `의존성 역전`을 위해 **N개의 if문을 제거 by OCP위반 처리**
    ![20220617125909](https://raw.githubusercontent.com/is2js/screenshots/main/20220617125909.png)

    - OCP위반 기초: 구상체(경우의 수)들이 if로 나열되면, OCP위반(구상체 추가시 if문 코드가 늘어남)이다.
    - if문 제거: 
        1. 각 경우의수를 아우르는 `추상체(interface) 생성`(없으면)
            - 각 구상체들이 메서드가 1개면 편하게 추상화해서 생성
            - 아니라면 공통로직 묶음을 생각해서 생성
        2. 구상체들의 로직안에서 1개의 추상메서드에 담을 생각하면서 **1개 메서드안에 담길 수 있게 `공통로직으로 추상화`해서 추상층의 메서드로 올리기** -> 
            - 추상화해야 외부로 선택권을 넘길 수 있다.
            - 공통로직을 만들어야하는데, **1개의 메서드에 공통로직이 담길 수 있는 만큼 묶어야하므로 input과 output을 생각한다.**
            - **추상화의 대상 공통로직 추출은 `추상층의 메서드1개로 올라간다`**
            - **input과 output의 공통로직 범위가 정해졌다면, 1개의 메서드를 생성해서 올린다.**
                - 만일 if 내부에 1개의 메서드만 사용되었다면, 그 메서드들의 명칭을 추상화해서 올리면 된다.
        3. `경우의 수만큼 구상형`(class)생성 -> 
        4. `외부`에서 원하는 형, `객체를 선택 생성해서 주입`하도록 외부방향으로 밀어냄.


    1. 이미 추상층은 Paper의 인터페이스로 생성되어 있는 상태다.
        - 추상층은 if를 만들어대는 구상체들을 통합한 인터페이스다.
    2. 메서드1개의 공통로직으로 추상화(추상층에 메서드1개로 올리기)
        1. `구상체들(ServerClient, Client)을 하나의 추상체로 생각`한 뒤, 각 if ServerClient / if Client의 로직에서 어디까지 묶을 수 있는지 살펴봐야한다.
            - 구상체 -> 추상체1개로 생각하여 그로 인해 일어나는 일들을 모두 공통로직으로 묶는다.
            - paper(구상체 무시)가 나오면, (경우의 수 무시)
            - paper의 필드를 보고 programmer들이 나오고
            - programmer들을 paper에 박은 뒤(한참 뒤에 나와서 setter)
            - 완성된 paper를 programmer들에게 던져주어, 프로그램(들)을 만들어낸다.
            - Director는 프로그램을 받아, 자신 내부에서 `객체없는 메소드`를 통해 deploy한다.
        ![20220617133707](https://raw.githubusercontent.com/is2js/screenshots/main/20220617133707.png)
        - **`명세(paper)에 따라 명세를 완성하고 -> 프로그래머들한테 프로그램을 맡기는`역할이 공통로직으로 추상화되어 if만들어내는 추상층(Paper)로 올라간다**
            - **if만들어내는 추상체에게 로직이동 = `역할위임`되었다.**
            - **Director의 역할(명세 채우고 프로그램 만들기) -> Paper의 역할로 `역할이 넘어갔지만, 객체지향에서는 객체 스스로도 일을 할 수 있어서 가능`하다**
            - 현실에서는 디렉터가 명세보고 프로그램 만들어내도록 지시하지만
                - **객체지향현실에서는 Paper가 자신의 명세를 채우고, 그에 따른 프로그램을 만들도록 프로그래머에게 시키는 일도 가능하다.**
        - **역할책임모델은 도메인과 일치하지 않는다. 적당한 역할을 하는 사람이 가져간다.**
            - **책임: `Director가 project를 실행하는 것`에서 -> `<여러 if분기를 유발하는 추상층> paper가 project를 run`하도록  `추상화`를 통해 넘어갔다.**


        

9. [실전] 구상체로 이루어진 if분기문 제거하기
    1. 클라이언트로 밀어내기 위해 `추상체` 없으면 생성 여기선 Paper가 있음.
    2. 