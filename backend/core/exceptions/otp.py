from .base import BaseException


class OtpLost(BaseException):
    code = 'otp_lost'
    msg = 'otp expire or not exist'


class OtpInvalid(BaseException):
    code = 'otp_invalid'
    msg = 'otp invalid'
