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
    item { Subtitle1() }
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
private fun Subtitle1() = Text(
    text = "Ни для кого не секрет, что в России права человека считаются чем-то, что всегда " +
            "уходит на задний план",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 16.dp)
)

@Composable
private fun Subtitle2() = Text(
    text = "Почтовые сервисы Яндекса, VK и Mail не являются безопастными - данные компании " +
            "по щелчку пальцев правительства могут слить ваши данные или заблокировать " +
            "учётную запись",
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
               "Зарегистрироваться с помощью них не получится"
    )
    StepItem(
        text = "Используйте анонимные почтовые сервисы, такие как ProtonMail. " +
               "Данный сервис находится в юрисдикции Швейцарии, поэтому, чтобы получить доступ " +
               "к вашему аккаунту, ФСБ понадобится предоставить кучу объективных аргументов " +
               "Европейским судам"
    )
    StepItem(
        text = "VPN - ваш друг. Желательно платный и с хорошей репутацией, чтобы избегать " +
                "сливов персональных данных"
    )
    StepItem(
        text = "Используйте генераторы рандомных паролей. Чем сложнее ваш пароль - тем сложнее " +
               "получить доступ к вашему аккаунту. Хранить такие пароли можно в 1Password " +
               "или же в связке паролей Apple"
    )
    StepItem(
        text = "Когда ходите на митинги - выключайте GPS. С его помощью можно отследить " +
               "ваше местоположение с точностью до метра"
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
    text =  "Забывая про безопастность своих данных, вы подвергаете себя риску. Не надо так",
    color = MaterialTheme.colors.textPrimary,
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
)