# Quizz question

#### What is displayed when this code is compiled and executed?

```java
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> pantry = new HashMap<>();

        pantry.put("Apples", 3);
        pantry.put("Oranges", 2);

        int currentApples = pantry.get("Apples");
        pantry.put("Apples", currentApples + 4);

        System.out.println(pantry.get("Apples"));
    }
}
```

- [ ] 6
- [ ] 3
- [ ] 4
- [X] 7


```shell
(base) onyr@aezyr:~/Documents/code/java/training/linkedin_assessment/what_is_displayed_0$ javac Main.java 
(base) onyr@aezyr:~/Documents/code/java/training/linkedin_assessment/what_is_displayed_0$ java Main *$
7
```
