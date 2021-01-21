# 에러해결

Execution failed for task ':JpashopApplication.main()'.

Process 'command 'C:/Program Files/Java/jdk-14.0.2/bin/java.exe'' finished with non-zero exit value 1

> 참고 : JpashopApplication 은 프로젝트 이름

<br>

스프링부트로 run server를 진행하자 갑자기 에러가 발생.

구글링해보니 이미 해당 포트를 사용하고 있어서 생기는 에러!

![20210120_095845](https://user-images.githubusercontent.com/59816811/105112323-20040d80-5b06-11eb-8396-ec33033522eb.png)

다음과 같이 전에 띄워놓은 서버가 있는데 내가 새롭게 서버를 또 띄워서 포트번호가 겹쳐서 생기는 에러였음!

--> 하나를 종료하고 하면 문제 해결