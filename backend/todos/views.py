from rest_framework.viewsets import mixins
from rest_framework.viewsets import GenericViewSet
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import status

from core.pagination import TodoPageNumberPagination
from todos.models import Todo
from todos.serializers.todo import CreateTodoSerializer, GetTodoSerializer, GetTodoListSerializer, DeleteTodoSerializer
from todos.services import TodoService


class TodoViewSet(mixins.CreateModelMixin, mixins.UpdateModelMixin,
                  mixins.RetrieveModelMixin, mixins.ListModelMixin,
                  mixins.DestroyModelMixin, GenericViewSet):
    lookup_field = 'pub_id'
    pagination_class = TodoPageNumberPagination

    def get_queryset(self):
        if self.action == 'list':
            return Todo.objects.filter(todo_user=self.request.user_object)
        return None

    def get_serializer_class(self):
        if self.action == 'list':
            return GetTodoListSerializer
        return None

    def create(self, request, *args, **kwargs):
        serializer = CreateTodoSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        # create todo
        validated_data['todo_user'] = request.user_object
        todo = TodoService.create_todo(**validated_data)

        return Response(
            status=status.HTTP_201_CREATED,
            data={
                'todo_id': todo.pub_id,
                'content': todo.content,
                'done': todo.done,
                'create_time': todo.created,
                'update_time': todo.modified,
            }
        )

    def destroy(self, request, *args, **kwargs):
        print(args)
        print(kwargs)
        return Response(status=status.HTTP_204_NO_CONTENT)
