package tk.zwander.commonCompose.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tk.zwander.common.data.changelog.Changelog
import tk.zwander.common.util.invoke
import tk.zwander.commonCompose.util.parseHtml
import tk.zwander.samloaderkotlin.resources.MR

@Composable
internal fun ChangelogDisplay(
    changelog: Changelog?,
) {
    OutlinedCard(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 500.dp)
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
        ) {
            val infoItems by derivedStateOf {
                val list = mutableListOf<String>()

                changelog?.relDate?.let { relDate ->
                    list.add(MR.strings.release(relDate))
                }

                changelog?.secPatch?.let { secPatch ->
                    list.add(MR.strings.security(secPatch))
                }

                list
            }

            if (infoItems.isNotEmpty()) {
                Text(
                    text = infoItems.joinToString("  •  "),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                )

            }

            if (changelog?.notes != null) {
                SelectionContainer {
                    val formatted = changelog.notes.replace("<br><br><br><br>", "<QUAD_BR>")
                        .replace("<br><br>", "<DOUBLE_BR>")
                        .replace("<br>\n", "\n")
                        .replace("<br>", "")
                        .replace("<QUAD_BR>", "\n\n")
                        .replace("<DOUBLE_BR>", "\n\n")

                    Text(formatted.parseHtml())
                }
            }
        }
    }
}
