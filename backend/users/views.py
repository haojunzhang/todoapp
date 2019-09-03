from rest_framework.viewsets import mixins
from rest_framework.viewsets import GenericViewSet
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import status

from .serializers.otp import SendOtpSerializer
from .serializers.users import SignUpSerializer, LoginSerializer, \
    VerifyEmailSerializer, ResetPasswordSerializer

from .models import TodoUser

from .services import OtpService, UserService

from core.exceptions.user import LoginFailed, UserAlreadyExist, UserDoesNotExist


class UserViewSet(mixins.CreateModelMixin, mixins.UpdateModelMixin,
                  mixins.RetrieveModelMixin, GenericViewSet):

    def create(self, request, *args, **kwargs):
        serializer = SignUpSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        # verify otp

        # create user
        user = UserService.create_user(**validated_data)

        # gen token
        api_token = UserService.set_login(user)

        return Response(
            data={
                'user_id': user.pub_id,
                'user_token': api_token
            },
            status=status.HTTP_201_CREATED
        )

    def retrieve(self, request, *args, **kwargs):
        user = request.user_object
        return Response(
            data={
                'user_id': user.pub_id,
                'email': user.email
            },
            status=status.HTTP_200_OK
        )

    @action(methods=['post'], detail=False)
    def email(self, request):
        """
        before sign up, verify email
        """
        serializer = VerifyEmailSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        # check exist email
        if TodoUser.objects.filter(email=validated_data['email']).exists():
            raise UserAlreadyExist

        # send otp
        otp_id = OtpService.send(validated_data['email'])

        return Response(
            data={
                'otp_id': otp_id
            },
            status=status.HTTP_201_CREATED
        )

    @action(methods=['post'], detail=False)
    def login(self, request):
        serializer = LoginSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        # check email exist
        try:
            user = TodoUser.objects.get(email=validated_data['email'])
        except TodoUser.DoesNotExist:
            raise LoginFailed()

        # check password
        if user.check_password(validated_data['password']):
            user.user_sign_pub_key = validated_data['user_sign_pub_key']
            user.save(update_fields=['user_sign_pub_key', 'modified'])

            return Response(
                data={
                    'user_id': user.pub_id,
                    'user_token': user.api_token
                },
                status=status.HTTP_200_OK
            )
        else:
            raise LoginFailed()

    @action(methods=['delete'], detail=False)
    def user_token(self, request):
        """
        logout
        """
        user = request.user_object

        # clear token
        UserService.logout(user)

        return Response(status=status.HTTP_204_NO_CONTENT)

    @action(methods=['put'], detail=False)
    def reset_password(self, request):
        serializer = ResetPasswordSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        # verify otp

        # set password

        return Response(status=status.HTTP_204_NO_CONTENT)


class OtpViewSet(mixins.CreateModelMixin, mixins.UpdateModelMixin,
                 mixins.RetrieveModelMixin, GenericViewSet):

    def create(self, request, *args, **kwargs):
        serializer = SendOtpSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        # check exist email
        if not TodoUser.objects.filter(email=validated_data['email']).exists():
            raise UserDoesNotExist

        # send otp
        otp_id = OtpService.send(validated_data['email'])

        return Response(
            data={
                'otp_id': otp_id
            },
            status=status.HTTP_201_CREATED
        )
