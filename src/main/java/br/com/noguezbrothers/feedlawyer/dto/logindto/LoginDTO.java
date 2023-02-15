package br.com.noguezbrothers.feedlawyer.dto.logindto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @NotNull
    @Schema(example = "tom.sawyer")
    private String login;
    @NotNull
    @Schema(example = "123")
    private String senha;
}
