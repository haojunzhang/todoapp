from rest_framework.viewsets import mixins
from rest_framework.viewsets import GenericViewSet
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import status

from todos.models import Todo
from todos.serializers.todo import CreateTodoSerializer, GetTodoSerializer


class TodoViewSet(mixins.CreateModelMixin, mixins.UpdateModelMixin,
                  mixins.RetrieveModelMixin, mixins.ListModelMixin,
                  GenericViewSet):
    lookup_field = 'pub_id'

    def get_queryset(self):
        if self.action == 'list':
            queryset = Todo.objects.filter(todo_user=self.request.user_object)
        elif self.action == 'retrieve':
            queryset = Todo.objects.filter(pub_id=self.kwargs['pub_id'])

    def create(self, request, *args, **kwargs):
        serializer = CreateTodoSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        return Response(status=status.HTTP_201_CREATED)

    def retrieve(self, request, *args, **kwargs):
        serializer = GetTodoSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        validated_data = serializer.validated_data

        return Response(status=status.HTTP_200_OK)
