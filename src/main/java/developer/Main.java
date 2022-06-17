package developer;

import developer.paper.Client;
import developer.progammer.FrontEnd;

public class Main {
    public static void main(String[] args) {
        final Director director = new Director();

        director.addProject("여행사A 프론트 개편", new Client() {
            // Client라는 project(paper)는 추상클래스-객체만들라면 추상메서드 run을 구현해서 완성해야함.
            // -> clinet형 paper을 완성해야한다.
            @Override
            public Program[] run() {
                //run()에는 Director내부에서 Paper instanceof마다 다르게 구현했던 내용들을 Main에서 구현함
                //1. Client형만 아는 FrontEnd개발자를 만든다.
                // - Paper형에 따라 개발자가 받을 수 있는 정보가 다르기 때문
                final FrontEnd<Client> frontEnd = new FrontEnd<>() {
                    @Override
                    protected void setData(final Client paper) {
                        //2. client형의 paper로부터 정보를 받아 FrontEnd의 필드를 채운다.
                        // - 추상클래스의 필드는 안보이지만 사용할 수 있다.
                        library = paper.getLibrary();
                        language = paper.getLanguage();
                    }
                };

                //2. 완성된 frontEnd를 client paper 자신에 박아준다
                // - 개발자는 paper의 정보를 받아 완성 -> paper도 그 개발자가 완성되어야 완성
                setProgrammer(frontEnd);

                //3. 완성된 개발자한테, 완성된 Client paper를 줘서 프로그램을 만든다.
                final Program program = frontEnd.getProgram(this);

                //4. 백엔드의 경우 프로그램이 2개가 나오므로, 공통 추상메서드로서 배열로 반환한다.
                return new Program[]{program};
            }
        });

        //5. Director내부에 project가 되었다면, 그 프로젝트로 runProject한다.
        // - director가 runProject하면 -> paper가 run()이 작동되어 program을 반환후 deploy까지 된다.
        director.runProject("여행사A 프론트 개편");
    }
}
