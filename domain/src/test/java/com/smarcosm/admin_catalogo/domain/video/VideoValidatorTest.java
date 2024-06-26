package com.smarcosm.admin_catalogo.domain.video;

import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.exception.DomainException;
import com.smarcosm.admin_catalogo.domain.genre.GenreID;
import com.smarcosm.admin_catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

public class VideoValidatorTest {

    @Test
    public void givenNullTitle_whenCallsValidate_shouldReceiveError(){
        //given
        final String expectedTitle = null;
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse video faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());
        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        // then
        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenEmptyTitle_whenCallsValidate_shouldReceiveError(){
        //given
        final var expectedTitle = "";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse video faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' should not be empty";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());
        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }

    @Test
    public void givenTitleWithLengthGreaterThan255_whenCallsValidate_shouldReceiveError(){
        //given
        final String expectedTitle = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse video faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                Esse video faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse video faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'title' must be between 1 and 255 characters";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());
        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }

    @Test
    public void givenEmptyDescription_whenCallsValidate_shouldReceiveError(){
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = "";
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' should not be empty";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());
        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }
    @Test
    public void givenDescriptionWithLengthGreaterThan4000_whenCallsValidate_shouldReceiveError(){
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """ 
                O Desenvolvimento Orientado a Testes (TDD) é uma abordagem de desenvolvimento de software que enfatiza a escrita de testes antes de escrever o código real. Vamos explorar os detalhes do TDD, seus benefícios e como ele se encaixa no ciclo de vida do desenvolvimento de software.
                O que é o Desenvolvimento Orientado a Testes (TDD)? O TDD é um processo estratégico em que os desenvolvedores criam casos de teste antes de implementar o código funcional. Vejamos como funciona:
                Escreva um Caso de Teste que Falha: Comece definindo um caso de teste que capture o comportamento desejado de uma funcionalidade específica.
                Esse caso de teste deve falhar inicialmente, pois ainda não há código correspondente para fazê-lo passar.
                Escreva o Mínimo de Código para Passar o Teste: Em seguida, escreva a quantidade mínima de código necessária para fazer o teste passar.
                O objetivo não é criar uma solução completa, mas sim satisfazer o caso de teste. Execute os Testes: Execute todos os testes automatizados, incluindo o novo que você acabou de escrever.
                Se algum teste falhar, revise o código e corrija os problemas. Refatore e Melhore: Após o teste passar, refatore o código para melhorar sua qualidade.
                A refatoração garante que o código seja mantido e siga as melhores práticas.
                Por que Usar o TDD? Detecção Precoce de Bugs: Ao escrever testes primeiro, você identifica possíveis problemas no início do processo de desenvolvimento.
                Corrigir bugs é mais fácil e econômico quando identificados precocemente. Qualidade do Código: O TDD incentiva código modular e bem estruturado.
                Os desenvolvedores focam em escrever código limpo e de fácil manutenção desde o início.
                Prevenção de Regressões: À medida que você adiciona novas funcionalidades ou modifica as existentes, o TDD garante que a funcionalidade atual permaneça intacta.
                Bugs de regressão são menos prováveis de ocorrer. Confiança na Refatoração: A refatoração se torna menos arriscada porque você tem um conjunto de testes como rede de segurança.
                Você pode fazer alterações com confiança, sem medo de quebrar a funcionalidade existente. As Três Fases do TDD
                Escreva o Teste Primeiro: Defina um caso de teste que capture o comportamento desejado.
                Esse teste deve ser executado automaticamente para garantir que ele passe antes de escrever o código real.
                Escreva o Código para Passar o Teste: Implemente o mínimo de código necessário para fazer o teste passar.
                Evite superengenharia nessa etapa. Refatore: Melhore o código sem alterar seu comportamento. Certifique-se de que os testes ainda passem após a refatoração.
                Conclusão O TDD não se limita apenas a testes unitários; ele abrange testes de aceitação, integração e regressão. Seguindo esse processo, os desenvolvedores criam software robusto e confiável. Portanto, da próxima vez que iniciar uma nova funcionalidade, lembre-se: escreva o teste primeiro!\s
                O Desenvolvimento Orientado a Testes (TDD) é uma abordagem de desenvolvimento de software que enfatiza a escrita de testes antes de escrever o código real. Vamos explorar os detalhes do TDD, seus benefícios e como ele se encaixa no ciclo de vida do desenvolvimento de software.
                O que é o Desenvolvimento Orientado a Testes (TDD)? O TDD é um processo estratégico em que os desenvolvedores criam casos de teste antes de implementar o código funcional. Vejamos como funciona:
                Escreva um Caso de Teste que Falha: Comece definindo um caso de teste que capture o comportamento desejado de uma funcionalidade específica.
                Esse caso de teste deve falhar inicialmente, pois ainda não há código correspondente para fazê-lo passar.
                Escreva o Mínimo de Código para Passar o Teste: Em seguida, escreva a quantidade mínima de código necessária para fazer o teste passar.
                O objetivo não é criar uma solução completa, mas sim satisfazer o caso de teste. Execute os Testes: Execute todos os testes automatizados, incluindo o novo que você acabou de escrever.
                Se algum teste falhar, revise o código e corrija os problemas. Refatore e Melhore: Após o teste passar, refatore o código para melhorar sua qualidade.
                A refatoração garante que o código seja mantido e siga as melhores práticas.
                Por que Usar o TDD? Detecção Precoce de Bugs: Ao escrever testes primeiro, você identifica possíveis problemas no início do processo de desenvolvimento.
                Corrigir bugs é mais fácil e econômico quando identificados precocemente. Qualidade do Código: O TDD incentiva código modular e bem estruturado.
                Os desenvolvedores focam em escrever código limpo e de fácil manutenção desde o início.
                """;
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'description' must be between 1 and 4000 characters";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());
        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }
    @Test
    public void givenNullLaunchedAt_whenCallsValidate_shouldReceiveError(){
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse video faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final  Year expectedLaunchedAt = null;
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'launchedAt' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());
        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }
    @Test
    public void givenNullRating_whenCallsValidate_shouldReceiveError(){
        //given
        final var expectedTitle = "System Design Interviews";
        final var expectedDescription = """
                Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                Esse video faz parte da Imersão Full Stack && Full Cycle.
                Para acessar todas as aulas, lives e desafios, acesse:
                https://imersao.fullcycle.com.br/
                """;
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final Rating expectedRating = null;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'rating' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        final var validator = new VideoValidator(actualVideo, new ThrowsValidationHandler());
        // when
        final var actualError = Assertions.assertThrows(DomainException.class, () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }
}
