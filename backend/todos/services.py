from todos.models import Todo


class TodoService:

    @staticmethod
    def create_todo(**fields):

        todo = Todo(**fields)
        todo.save()

        return todo
