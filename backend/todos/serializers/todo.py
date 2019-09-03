from rest_framework import serializers


class CreateTodoSerializer(serializers.Serializer):
    content = serializers.CharField(required=True)


class GetTodoSerializer(serializers.Serializer):
    pass
