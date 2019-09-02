from rest_framework.viewsets import mixins
from rest_framework.viewsets import GenericViewSet
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import status

from .serializers.otp import SendOtpSerializer
from .serializers.users import SignUpSerializer, UserDisplaySerializer, LoginSerializer, EmailSerializer

from .models import TodoUser

from .services import OtpService, UserService

from core.exceptions.user import LoginFailed


def send_otp(request):
    serializer = EmailSerializer(data=request.data)
    serializer.is_valid(raise_exception=True)
    validated_data = serializer.validated_data
    email = validated_data['email']
    otp_id = OtpService().send(email)

    return Response(
        data={
            'otp_id': otp_id
        },
        status=status.HTTP_200_OK
    )


class UserViewSet(mixins.CreateModelMixin, mixins.UpdateModelMixin,
                  mixins.RetrieveModelMixin, GenericViewSet):
    lookup_filed = 'pub_id'

    def get_serializer_class(self):
        if self.action == 'create':
            serializer_class = SignUpSerializer
        elif self.action == 'retrieve':
            serializer_class = UserDisplaySerializer
        return serializer_class

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
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
        user = request.todo_user
        return Response(
            data={
                'user_id': user.pub_id,
                'email': user.email
            },
            status=status.HTTP_200_OK
        )

    @action(methods=['post'], detail=False)
    def login(self, request):
        serializer = LoginSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        try:
            user = TodoUser.objects.get(email=validated_data['email'])
        except TodoUser.DoesNotExist:
            raise LoginFailed()

        if user.check_password(validated_data['password']):
            user.sign_pub_key = validated_data['user_sign_pub_key']
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
    def logout(self, request):
        user = request.todo_user
        UserService.logout(user)
        return Response(status=status.HTTP_204_NO_CONTENT)

    @action(methods=['put'], detail=False)
    def reset_password(self, request):
        pass


class OtpViewSet(mixins.CreateModelMixin, mixins.UpdateModelMixin,
                 mixins.RetrieveModelMixin, GenericViewSet):

    def create(self, request, *args, **kwargs):
        serializer = SendOtpSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data
        print(validated_data)
        # send otp
        otp_id = OtpService.send(validated_data['email'])

        return Response(
            data={
                'otp_id': otp_id
            },
            status=status.HTTP_201_CREATED
        )
