from rest_framework import serializers


class CreateTodoSerializer(serializers.Serializer):
    content = serializers.CharField(required=True)


class GetTodoSerializer(serializers.Serializer):
    pass


class GetTodoListSerializer(serializers.Serializer):

    def to_representation(self, instance):
        return {
            'content': instance.content
        }
