package com.smarcosm.admin_catalogo.domain;

import com.github.javafaker.Faker;
import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberType;
import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.genre.Genre;
import com.smarcosm.admin_catalogo.domain.video.Rating;
import com.smarcosm.admin_catalogo.domain.video.Resource;
import com.smarcosm.admin_catalogo.domain.video.Video;

import java.time.Year;
import java.util.Set;

import static io.vavr.API.*;

public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static Integer year() {
        return FAKER.random().nextInt(2020, 2030);
    }

    public static Double duration() {
        return FAKER.options().option(120.0, 15.5, 35.5, 10.0, 2.0);
    }

    public static boolean bool() {
        return FAKER.bool().bool();
    }

    public static String title() {
        return FAKER.options().option(
                "System Design no Mercado Livre na prática",
                "Não cometa esses erros ao trabalhar com Microsserviços",
                "Testes de Mutação. Você não testa seu software corretamente"
        );
    }


    public static Video video() {
        return Video.newVideo(
                Fixture.title(),
                Videos.description(),
                Year.of(Fixture.year()),
                Fixture.duration(),
                Fixture.bool(),
                Fixture.bool(),
                Videos.rating(),
                Set.of(Categories.aulas().getId()),
                Set.of(Genres.tech().getId()),
                Set.of(CastMembers.wesley().getId(), CastMembers.sebastiao().getId())
        );
    }

    public static final class Categories {

        private static final Category AULAS =
                Category.newCategory("Aulas", "Some description", true);

        private static final Category LIVES =
                Category.newCategory("Lives", "Some description", true);

        public static Category aulas() {
            return AULAS.clone();
        }

        public static Category lives() {
            return LIVES.clone();
        }
    }

    public static final class CastMembers {

        private static final CastMember WESLEY =
                CastMember.newMember("Wesley FullCycle", CastMemberType.ACTOR);

        private static final CastMember SEBASTIAO =
                CastMember.newMember("Sebastiao FullCycle", CastMemberType.ACTOR);

        public static CastMemberType type() {
            return FAKER.options().option(CastMemberType.values());
        }

        public static CastMember wesley() {
            return CastMember.with(WESLEY);
        }

        public static CastMember sebastiao() {
            return CastMember.with(SEBASTIAO);
        }
    }

    public static final class Genres {

        private static final Genre TECH =
                Genre.newGenre("Technology", true);

        private static final Genre BUSINESS =
                Genre.newGenre("Business", true);

        public static Genre tech() {
            return Genre.with(TECH);
        }

        public static Genre business() {
            return Genre.with(BUSINESS);
        }
    }

    public static final class Videos {

        private static final Video SYSTEM_DESIGN = Video.newVideo(
                "System Design no Mercado Livre na prática",
                description(),
                Year.of(2022),
                Fixture.duration(),
                Fixture.bool(),
                Fixture.bool(),
                rating(),
                Set.of(Categories.aulas().getId()),
                Set.of(Genres.tech().getId()),
                Set.of(CastMembers.wesley().getId(), CastMembers.sebastiao().getId())
        );

        public static Video systemDesign() {
            return Video.with(SYSTEM_DESIGN);
        }

        public static Rating rating() {
            return FAKER.options().option(Rating.values());
        }

        public static Resource resource(final Resource.Type type) {
            final String contentType = Match(type).of(
                    Case($(List(Resource.Type.VIDEO, Resource.Type.TRAILER)::contains), "video/mp4"),
                    Case($(), "image/jpg")
            );

            final byte[] content = "Conteudo".getBytes();

            return Resource.with(content, contentType, type.name().toLowerCase(), type);
        }

        public static String description() {
            return FAKER.options().option(
                    """
                            Disclaimer: o estudo de caso apresentado tem fins educacionais e representa nossas opiniões pessoais.
                            Esse vídeo faz parte da Imersão Full Stack && Full Cycle.
                            Para acessar todas as aulas, lives e desafios, acesse:
                            https://imersao.fullcycle.com.br/
                            """,
                    """
                            Nesse vídeo você entenderá o que é DTO (Data Transfer Object), quando e como utilizar no dia a dia, 
                            bem como sua importância para criar aplicações com alta qualidade.
                            """
            );
        }
    }
}