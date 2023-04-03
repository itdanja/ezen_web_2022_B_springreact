package ezenweb.example.day01;

public class Ex1 {
    public static void main(String[] args) {
        // 1. dto
        Dto dto = new Dto( 1 , "qwe","qwe",100,"010-4444-4444");

        // 2. lombok [ @NoArgsConstructor ]
        LombokDto lombokDto1  = new LombokDto();

        // 2. lombok [ @AllArgsConstructor  ]
        LombokDto lombokDto2  = new LombokDto( 1 , "qwe" , "qwe" , 100 , "010-4444-4444");

        // 2. lombok [ @Setter@Getter  ]
        System.out.println( "getter : " + lombokDto2.getMid() );
        lombokDto1.setMid("asd");

        // 2. lombok [ @ToString  ]
        System.out.println(" tosring() : " + lombokDto2.toString() );

        // 2. lombok [ @Builder ] ******
        LombokDto lombokDto3 = LombokDto.builder()
                .mpassword("qwe")
                .mid("qwe").build();
        System.out.println(" tosring() : " + lombokDto3.toString() );
            // 생성자 안쓰고 아이디와 패스워드가 저장된 객체 생성
            // 객체 생성시 매개변수 개수 상관X 순서 상관X
    }
}
