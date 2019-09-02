from rest_framework import serializers


class SendOtpSerializer(serializers.Serializer):
    email = serializers.EmailField(required=True)

    def validate_email(self, value):
        if '@' not in value:
            raise serializers.ValidationError('email format is not valid')
        return value
