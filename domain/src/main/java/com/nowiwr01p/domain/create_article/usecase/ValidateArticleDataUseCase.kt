package com.nowiwr01p.domain.create_article.usecase

import com.nowiwr01p.core.model.Article
import com.nowiwr01p.domain.UseCase
import com.nowiwr01p.domain.create_article.validators.CreateArticleValidator
import com.nowiwr01p.domain.create_article.validators.data.CreateArticleError

class ValidateArticleDataUseCase(
    private val validator: CreateArticleValidator
): UseCase<Article, List<CreateArticleError>> {

    override suspend fun execute(input: Article): List<CreateArticleError> {
        return validator.validate(input)
    }
}