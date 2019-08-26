from django.contrib.auth.base_user import AbstractBaseUser
from django.db import models

from core.models import AutoPubIDField, CreatedAndModifiedMixin


class TodoUser(CreatedAndModifiedMixin, AbstractBaseUser):
    class Meta:
        verbose_name = 'todo_user'

    USERNAME_FIELD = 'pub_id'  # AbstractBaseUser needed
    EMAIL_FIELD = 'email'  # AbstractBaseUser needed

    pub_id = AutoPubIDField(
        'public id'
    )

    email = models.EmailField(
        'email',
        unique=True,
        max_length=50
    )

    user_sign_pub_key = models.CharField(
        'user sign pub key',
        unique=True,
        max_length=255
    )

    api_token = models.CharField(
        'api token',
        unique=True,
        max_length=255,
        null=True
    )

    def __str__(self):
        return f'{self.email}'
