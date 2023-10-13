package com.example.buysell.Annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
//взято с сайта https://devcolibri.com/spring-mvc-%D0%BA%D0%B0%D1%81%D1%82%D0%BE%D0%BC%D0%BD%D0%B0%D1%8F-%D0%B0%D0%BD%D0%BD%D0%BE%D1%82%D0%B0%D1%86%D0%B8%D1%8F-%D0%B4%D0%BB%D1%8F-%D0%B2%D0%B0%D0%BB%D0%B8%D0%B4%D0%B0%D1%86%D0%B8%D0%B8/
@Documented  //указывает, что помеченная таким образом аннотация должна быть добавлена в javadoc поля/метода.
@Constraint(validatedBy = PhoneConstraintValidator.class)  //список реализаций данного интерфейса
@Target({ElementType.METHOD, ElementType.FIELD}) //указывает, что именно мы можем пометить этой аннотацией.
@Retention(RetentionPolicy.RUNTIME ) //позволяет указать жизненный цикл аннотации: будет она присутствовать только в исходном коде,
                                     // в скомпилированном файле, или она будет также видна и в процессе выполнения.
public @interface Phone {

    String message () default "{doesn't seem to be valid phone number}";
    Class<?> [] groups () default {};
    Class<? extends Payload> [] payload() default {} ;

}
