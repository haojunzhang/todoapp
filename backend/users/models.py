import secrets

from django.contrib.auth.base_user import AbstractBaseUser
from django.db import models

from core.models import AutoPubIDField, CreatedAndModifiedMixin


def generate_user_token():
    return secrets.token_hex()


TYPE_APP_USER = 'AppUser'
TYPE_TODO_USER = 'TodoUser'


class AppUser(CreatedAndModifiedMixin):
    class Meta:
        verbose_name_plural = 'app_users'
        verbose_name = 'app_user'

    pub_id = AutoPubIDField(
        'public id',
        db_index=True,
    )

    api_token = models.CharField(
        'api token',
        unique=True,
        max_length=255,
        db_index=True,
        default=generate_user_token,
    )

    user_sign_pub_key = models.CharField(
        'user sign pub key',
        unique=True,
        max_length=511,
    )

    name = models.CharField(
        'name',
        max_length=32,
    )

    def __str__(self):
        return f'{self.name}'


class TodoUser(CreatedAndModifiedMixin, AbstractBaseUser):
    class Meta:
        verbose_name_plural = 'todo_users'
        verbose_name = 'todo_user'

    USERNAME_FIELD = 'pub_id'  # AbstractBaseUser needed
    EMAIL_FIELD = 'email'  # AbstractBaseUser needed

    pub_id = AutoPubIDField(
        'public id',
        db_index=True,
    )

    email = models.EmailField(
        'email',
        unique=True,
        max_length=50,
    )

    user_sign_pub_key = models.CharField(
        'user sign pub key',
        unique=True,
        max_length=511,
    )

    api_token = models.CharField(
        'api token',
        unique=True,
        max_length=255,
        null=True,
    )

    def __str__(self):
        return f'{self.email}'
