package study.rawjpa.sample;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class SampleVO {

    @NotBlank
    private String key1;

    private String key2;

    @Email
    @NotBlank
    private String email;
}
