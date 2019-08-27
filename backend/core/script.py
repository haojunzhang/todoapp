import os
import sys

parent_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
sys.path.append(parent_path)
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "core.settings")

import django

django.setup()

from users.models import AppUser


def get_key_text(path):
    with open(path, 'r') as f:
        text = ''
        for l in f:
            if '-----' not in l:
                text += l
        text = text.replace('\n', '')
        return text


pub_id = 'app_user_pub_id_1'
api_token = 'app_user_api_token_1'
if not AppUser.objects.filter(pub_id=pub_id, api_token=api_token).exists():
    app_user = AppUser()
    app_user.pub_id = pub_id
    app_user.api_token = api_token
    app_user.user_sign_pub_key = get_key_text('keys/app_user_sign_pub.pem')
    app_user.name = 'AppUser'
    app_user.save()
    print('add default app user success')
