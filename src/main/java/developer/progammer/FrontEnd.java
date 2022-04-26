package developer.progammer;

import developer.Language;
import developer.Library;
import developer.Program;
import developer.paper.Client;
import developer.paper.Paper;

public class FrontEnd implements Programmer {

    private Language language;
    private Library library;

    public Program makeProgram(final Paper paper) {
        //1. 추상체 통로에서 추상체.전략메서드 호출()로, 외부 주입 구상체로부터 알아서 실행되게 할 것 아니면 - 전략패턴
        // paper.paper에_적힌_명세대로_여기프론트한테_language+library_set() 해줘야하는데..?

        //2. 전략메서드가 몰빵이 아니라면, 구상체마다 instanceof로 검사해서 처리해야한다.
        // - 모든 구상체들의 공통메서드인 전략메서드가 없으면, 개별처리는 개별 확인해서 할 수 밖에 없다.
        if (paper instanceof Client) {
            //paper 중 Client paper를 건네 받을 때만, 거기에 적힌 것들을 꺼내서, frontEnd 필드들에 넣어준다.
            // -> 마커인터페이스Paper에는 정보가 없어, 구상체로 다운캐스팅(if instanceof)을 하는데,
            // -> OCP위반 = 부모를 자식으로 바꿔야하는 상황 = if instance of = Open(확장)될수록 분기가 늘어나 Close(코드수정 방지)가 안됨
            // --> 그래도 Client임을 확인하고, 거기서 정보를 빼내서 세팅되어야 -> 자신만의 프로그램을 만들어낼 수 있는 상황이다.
            final Client pb = (Client) paper;
            language = pb.getLanguage();
            library = pb.getLibrary();
        }

        // [객체생략메서드] = [자기만의 기능] = this 등 내부필드들을 사용하는 내부에서 정의한 메서드
        // -> 외부 명세에서 정보를 입력받으면, 그 정보들로 [자기만의 프로그램]을 만든다.
        return makeFrontEndProgram();
    }

    private Program makeFrontEndProgram() {
        return new Program();
    }
}
