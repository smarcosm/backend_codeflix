package com.smarcosm.admin_catalogo.application.category.update;

import com.smarcosm.admin_catalogo.application.UseCase;
import com.smarcosm.admin_catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
    extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>>
{

}
