import http.server as https
import logging
import logging.config
import socketserver
from typing import Any

import jwt

PORT = 9089

logging.config.dictConfig({
    "version": 1,
    "disable_existing_loggers": False,
    "formatters": {
        "default": {
            "format": "[%(asctime)s] - %(name)-s - %(levelname)-s - %(lineno)d - %(message)s",
            "datefmt": "%Y-%m-%d %H:%M:%S"
        }
    },
    "handlers": {
        "console": {
            "class": "logging.StreamHandler",
            "formatter": "default",
            "level": "DEBUG",
            "stream": "ext://sys.stdout"
        }
    },
    "loggers": {
        "console": {
            "level": "DEBUG",
            "handlers": ["console"],
            "propagate": True
        }
    },
    'root': {
        'level': 'INFO',
        'handlers': ['console'],
        'propagate': True
    }
})

log = logging.getLogger('HANDLER')

# Mock product signature
VALID_PRODUCT_SIGNATURE = "108.0.11//31039874187387492312RHA#!93813!//333.1D"

# Mock valid product code
VALID_PRODUCT_CODE = "108.0.11"

# Mock valid key
VALID_PRODUCT_KEY = "12345"

# Mock valid user
VALID_USERNAME = "kencinas"

# Mock valid password
VALID_PASSWORD = "00test00!"


class Handler(https.SimpleHTTPRequestHandler):
    mocks = {
        'code': VALID_PRODUCT_CODE,
        'key': VALID_PRODUCT_KEY,
        'signature': VALID_PRODUCT_SIGNATURE,
        'username': VALID_USERNAME,
        'password': VALID_PASSWORD
    }

    def log_message(self, fmt: str, *args: Any) -> None:
        pass

    def do_GET(self):
        match self.path:
            case "/setup":
                self._setup()
            case "/activate":
                self._activate()
            case _:
                self._not_found()

    def _activate(self):
        """
        GET /activate
        LP_REQUESTER: jwt(<username><password>)
        """
        request = self.headers.get("LP_ACTIVATION_REQUEST")
        if not request:
            self.send_response(400, "Missing LP_ACTIVATION_REQUEST header.")
            self.end_headers()
            log.error("Missing LP_ACTIVATION_REQUEST header")
            return

        try:
            payload = jwt.decode(request, self.mocks["signature"], algorithms=["HS384"])
        except jwt.InvalidTokenError as ex:
            self.send_response(400, "Header LP_ACTIVATION_REQUEST has an invalid value.")
            log.error(f"Header LP_ACTIVATION_REQUEST has an invalid value {ex}")
            self.end_headers()
            return

        username = payload.get("sub")
        if not username:
            self.send_response(400, "Missing username for this action.")
            log.error("Missing username for this action.")
            self.end_headers()
            return

        password = payload.get("pwd")
        if not password:
            self.send_response(400, "Missing password for this action.")
            log.error("Missing password for this action.")
            self.end_headers()
            return

        code = payload.get("iss")
        if not username:
            self.send_response(400, "Missing product code for this action.")
            log.error("Missing product code for this action.")
            self.end_headers()
            return

        key = payload.get("key")
        if not username:
            self.send_response(400, "Missing key for this action.")
            log.error("Missing key for this action.")
            self.end_headers()
            return

        user_checks = [
            self.mocks.get('username') == username,
            self.mocks.get('password') == password
        ]

        product_checks = [
            self.mocks.get('code') == code,
            self.mocks.get('key') == key
        ]

        if not all(user_checks):
            self.send_response(401, "Cannot validate requester user.")
            self.end_headers()
            log.error(f"Cannot validate requester user <{username}:{password}>")
            return

        if not all(product_checks):
            self.send_response(420, "Cannot validate requested product.")
            self.end_headers()
            log.error(f"Cannot validate requested product <{code}:{key}>")
            return

        self.send_response(200)
        self.end_headers()

    def _setup(self):
        pass

    def _not_found(self):
        pass


if __name__ == '__main__':
    with socketserver.TCPServer(("", 8089), Handler) as httpd:
        httpd.serve_forever()
