package com.nowiwr01p.auth.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core_ui.theme.textPrimary
import com.nowiwr01p.core_ui.theme.title1Bold

@Preview(showBackground = true)
@Composable
fun AuthSecurityWarningContent() = LazyColumn(
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
) {
    item { Title() }
    item { Subtitle2() }
    item { StepsTitle() }
    item { Steps() }
    item { Subtitle3() }
}

@Composable
private fun Title() = Text(
    text = "Про безопастность",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.title1Bold,
    modifier = Modifier.padding(top = 16.dp)
)

@Composable
private fun Subtitle2() = Text(
    text = "Почтовые сервисы Yandex, VK и Mail не являются безопастными - сливы данных пользователей " +
            "происходят +- часто. В последней утечке Яндекса слили не только личные данные " +
            "пользователей, но и личные данные сотрудников.",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 8.dp)
)

@Composable
private fun StepsTitle() = Text(
    text = "Чтобы обезопасить свои данные, пожалуйста",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 8.dp)
)

@Composable
private fun Steps() = Column {
    StepItem(
        text = "Не используйте почтовые сервисы, связанные с РФ. " +
               "Зарегистрироваться с помощью них не получится."
    )
    StepItem(
        text = "Используйте анонимные почтовые сервисы, такие как ProtonMail. " +
               "При регистрации в ProtonMail не требуется вводить номер телефона или другой адрес " +
                "электройнной почты, а сам сервис находится в юрисдикции Швейцарии."
    )
    StepItem(
        text = "Используйте генераторы рандомных паролей. Чем сложнее ваш пароль - тем сложнее " +
               "получить доступ к вашему аккаунту. Хранить такие пароли можно, например, в 1Password."
    )
}

@Composable
private fun StepItem(text: String) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp, bottom = 6.dp, start = 20.dp, end = 20.dp)
) {
    Text(
        text = "–",
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.textPrimary
    )
    Spacer(modifier = Modifier.width(12.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.textPrimary
    )
}

@Composable
private fun Subtitle3() = Text(
    text =  "Забывая про безопастность своих данных, вы подвергаете себя риску.",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
)