import secrets
import random

from django.core.cache import cache
from django.db.models import Q

from core.models import AutoPubIDField
from core.exceptions.otp import OtpLost, OtpInvalid
from core.exceptions.user import UserAlreadyExist

from .models import TodoUser, generate_user_token


class UserService:

    @staticmethod
    def create_user(**fields):
        password = fields.pop('password')
        otp_id = fields.pop('otp_id')
        otp = fields.pop('otp')

        # check otp
        cache_otp = cache.get(f'otp:{otp_id}')

        # expire or not exist
        if not cache_otp:
            raise OtpLost

        # invalid
        if cache_otp != otp:
            raise OtpInvalid

        # check exist
        if TodoUser.objects.filter(
                Q(email=fields.get('email')) | Q(user_sign_pub_key=fields.get('user_sign_pub_key'))).count() > 0:
            raise UserAlreadyExist

        user = TodoUser(**fields)
        user.set_password(password)
        user.save()
        return user

    @staticmethod
    def set_login(user):
        api_token = generate_user_token()
        user.api_token = api_token
        user.save()
        return api_token

    @staticmethod
    def logout(user):
        user.api_token = None
        user.save()


class OtpService:

    def __init__(self, *args, **kwargs):
        pass

    @staticmethod
    def _generate_otp_value():
        return ''.join([str(random.randint(0, 9)) for _ in range(6)])

    @staticmethod
    def send(email):
        # generate id, value, text
        otp_id = AutoPubIDField().create_pushid()
        otp_value = OtpService._generate_otp_value()
        otp_text = f'your verification code {otp_value}'

        # send
        print(otp_text)

        # set cache(3 min expire)
        k = f'otp:{otp_id}'
        v = otp_value
        cache.set(k, v, 60 * 3)

        return otp_id
