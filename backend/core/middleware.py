import json
import logging

from django.utils.deprecation import MiddlewareMixin

from core.cache import get_user_cache_object
from core.responses import ResponseInvalidToken, ResponseInvalidSignature, ResponseInvalidTimeStamp
from core.key_utils import verify
from core.time_utils import get_timestamp
from users.models import TYPE_APP_USER, TYPE_TODO_USER, TodoUser, AppUser

logger = logging.getLogger(__name__)


# know which user
class TokenCheckMiddleware(MiddlewareMixin):

    def process_request(self, request):
        # AppUser xxx
        raw_token = request.headers.get('X-TODO-TOKEN')

        if not raw_token:
            return ResponseInvalidToken

        try:
            user_type, token = raw_token.split()
        except ValueError:
            return ResponseInvalidToken
        else:
            if user_type not in [TYPE_APP_USER, TYPE_TODO_USER]:
                return ResponseInvalidToken

        user_cache_object = get_user_cache_object(user_type, token)

        if not user_cache_object:
            return ResponseInvalidToken

        if user_type == TYPE_APP_USER:
            request.user_object = AppUser.objects.get(id=user_cache_object['id'])
        elif user_type == TYPE_TODO_USER:
            request.user_object = TodoUser.objects.get(id=user_cache_object['id'])

        request.user_type = user_type
        request.token = token
        request.user_cache_object = user_cache_object


# client time too diff from server time
class TimeStampCheckMiddleware(MiddlewareMixin):

    def process_request(self, request):
        try:
            if request.method in {'GET', 'DELETE'}:
                ts = request.GET.get('ts')
            else:
                data = json.loads(request.body)
                ts = data['ts']
        except Exception:
            return ResponseInvalidTimeStamp

        diff_millisecond = get_timestamp() - int(ts)

        if diff_millisecond > 1000:
            return ResponseInvalidTimeStamp


# ensure data not be changed
class VerifySignatureMiddleware(MiddlewareMixin):

    def process_request(self, request):

        signature = request.headers.get('X-TODO-SIGNATURE')

        if not signature:
            return ResponseInvalidSignature

        if request.method in {'GET', 'DELETE'}:
            get_dict = dict(request.GET)
            sorted_get_dict = {k: v[0] for k, v in sorted(get_dict.items())}
            sorted_query_string = "&".join(f"{k}={v}" for k, v in sorted_get_dict.items())
            text = sorted_query_string

        else:
            text = request.body

        if not verify(text, signature, request.user_cache_object['user_sign_pub_key']):
            return ResponseInvalidSignature
