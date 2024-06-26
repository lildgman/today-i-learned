package com.odg.spring.spring_core.singleton;

public class SingletonService {

    // 1. static 영역에 객체를 1개만 생성
    private static final SingletonService instance = new SingletonService();

    // 2. public으로 열어 객체 인스턴스 필요 시 이 static 메서드를 통해서만 조회하도록 하자
    public static SingletonService getInstance() {
        return instance;
    }

    // 3. 생성자를 private으로 선언하여 외부에서 new 키워드를 사용해서 객체 생성하는 것을 막음
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
