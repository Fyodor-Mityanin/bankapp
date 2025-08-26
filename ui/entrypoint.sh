#!/bin/sh

# Генерация env.js
cat <<EOF > /usr/share/nginx/html/env.js
window._env_ = {
  API_HOST: "${API_HOST:-http://localhost:8080}"
}
EOF

exec "$@"
