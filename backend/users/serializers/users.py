from rest_framework import serializers

from core.key_utils import decrypt, APP_USER_ENC_PRI_KEY
from users.services import UserService


class UserDisplaySerializer(serializers.Serializer):

    def to_representation(self, instance):
        result = {
            'user_id': instance.pub_id,
            'email': instance.email,
            'phone': instance.phone,
            'name': instance.name
        }
        return result


class EmailSerializer(serializers.Serializer):
    email = serializers.EmailField(required=True)


class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField(required=True)
    password = serializers.CharField(required=True)
    user_sign_pub_key = serializers.CharField(required=True)

    def validate_email(self, value):
        if '@' not in value:
            raise serializers.ValidationError('email format is not valid')
        return value

    def validate_password(self, value):
        decrypted_value = decrypt(value, APP_USER_ENC_PRI_KEY)
        if not decrypted_value:
            raise serializers.ValidationError('password is not valid')

        return decrypted_value

class SignUpSerializer(serializers.Serializer):
    email = serializers.EmailField(required=True)
    password = serializers.CharField(required=True)
    user_sign_pub_key = serializers.CharField(required=True)
    otp_id = serializers.CharField(required=True)
    otp = serializers.CharField(required=True)

    def validate_email(self, value):
        if '@' not in value:
            raise serializers.ValidationError('email format is not valid')
        return value

    def validate_password(self, value):
        decrypted_value = decrypt(value, APP_USER_ENC_PRI_KEY)
        if not decrypted_value:
            raise serializers.ValidationError('password is not valid')

        return decrypted_value

    def create(self, validated_data):
        user = UserService.create_user(**validated_data)
        return user
