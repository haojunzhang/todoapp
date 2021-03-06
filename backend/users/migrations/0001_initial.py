# Generated by Django 2.2.4 on 2019-08-27 07:01

import core.models
from django.db import migrations, models
import users.models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='AppUser',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('created', models.DateTimeField(auto_now_add=True)),
                ('modified', models.DateTimeField(auto_now_add=True)),
                ('pub_id', core.models.AutoPubIDField(blank=True, db_index=True, editable=False, max_length=20, unique=True, verbose_name='public id')),
                ('api_token', models.CharField(db_index=True, default=users.models.generate_user_token, max_length=255, unique=True, verbose_name='api token')),
                ('user_sign_pub_key', models.CharField(max_length=511, unique=True, verbose_name='user sign pub key')),
                ('name', models.CharField(max_length=32, verbose_name='name')),
            ],
            options={
                'verbose_name': 'app_user',
                'verbose_name_plural': 'app_users',
            },
        ),
        migrations.CreateModel(
            name='TodoUser',
            fields=[
                ('password', models.CharField(max_length=128, verbose_name='password')),
                ('last_login', models.DateTimeField(blank=True, null=True, verbose_name='last login')),
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('created', models.DateTimeField(auto_now_add=True)),
                ('modified', models.DateTimeField(auto_now_add=True)),
                ('pub_id', core.models.AutoPubIDField(blank=True, db_index=True, editable=False, max_length=20, unique=True, verbose_name='public id')),
                ('email', models.EmailField(max_length=50, unique=True, verbose_name='email')),
                ('user_sign_pub_key', models.CharField(max_length=511, unique=True, verbose_name='user sign pub key')),
                ('api_token', models.CharField(max_length=255, null=True, unique=True, verbose_name='api token')),
            ],
            options={
                'verbose_name': 'todo_user',
                'verbose_name_plural': 'todo_users',
            },
        ),
    ]
