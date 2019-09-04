from rest_framework import serializers


class CreateTodoSerializer(serializers.Serializer):
    content = serializers.CharField(required=True)


class GetTodoSerializer(serializers.Serializer):

    def to_representation(self, instance):
        return {
            'todo_id': instance.pub_id,
            'content': instance.content,
            'done': instance.done,
            'create_time': instance.created,
            'update_time': instance.modified,
        }


class GetTodoListSerializer(serializers.Serializer):

    def to_representation(self, instance):
        return {
            'todo_id': instance.pub_id,
            'content': instance.content,
            'done': instance.done,
            'create_time': instance.created,
            'update_time': instance.modified,
        }
