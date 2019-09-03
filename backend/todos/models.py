from django.db import models

from core.models import CreatedAndModifiedMixin, AutoPubIDField


class Todo(CreatedAndModifiedMixin):
    class Meta:
        verbose_name_plural = 'todos'
        verbose_name = 'todo'

    pub_id = AutoPubIDField(
        'public id',
        db_index=True,
    )

    content = models.CharField(
        'content',
        max_length=100,
    )

    done = models.BooleanField(
        'done',
        default=False,
    )

    todo_user = models.ForeignKey(
        'users.TodoUser',
        on_delete=models.PROTECT,
    )

    def __str__(self):
        return self.content
