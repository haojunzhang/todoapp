from django.core.cache import cache
from django.core.exceptions import ObjectDoesNotExist

from users.models import TYPE_APP_USER, TodoUser, TYPE_TODO_USER, AppUser


def set_user_cache_object(user_type, user):
    k = f'{user_type}:{user.api_token}'
    v = {
        'id': user.id,
        'user_sign_pub_key': user.user_sign_pub_key
    }
    cache.set(k, v)
    return v


def get_user_cache_object(user_type, token):
    k = f'{user_type}:{token}'
    v = cache.get(k)

    if v:
        return v
    else:
        try:
            if user_type == TYPE_APP_USER:
                user = AppUser.objects.get(api_token=token)
            elif user_type == TYPE_TODO_USER:
                user = TodoUser.objects.get(api_token=token)

            return set_user_cache_object(user_type, user)
        except ObjectDoesNotExist:
            return None
