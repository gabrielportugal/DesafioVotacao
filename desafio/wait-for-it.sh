#!/usr/bin/env bash
#   Use this script to test if a given TCP host/port are available
#   Original: https://github.com/vishnubob/wait-for-it

set -e

TIMEOUT=15
QUIET=0
HOST=""
PORT=""
CMD=""

while [[ $# -gt 0 ]]; do
    case "$1" in
        -h|--host)
            HOST="$2"
            shift 2
            ;;
        -p|--port)
            PORT="$2"
            shift 2
            ;;
        --)
            shift
            CMD="$@"
            break
            ;;
        *)
            if [[ "$1" =~ ^[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+:[0-9]+$ ]]; then
                HOSTPORT=(${1//:/ })
                HOST=${HOSTPORT[0]}
                PORT=${HOSTPORT[1]}
                shift
            else
                shift
            fi
            ;;
    esac
done

if [[ "$HOST" == "" || "$PORT" == "" ]]; then
    echo "Usage: $0 host:port -- command args"
    exit 1
fi

for i in $(seq $TIMEOUT); do
    nc -z "$HOST" "$PORT" >/dev/null 2>&1 && break
    sleep 1
    if [[ $i -eq $TIMEOUT ]]; then
        echo "Timeout after $TIMEOUT seconds waiting for $HOST:$PORT"
        exit 1
    fi
    done

if [[ $QUIET -ne 1 ]]; then
    echo "$HOST:$PORT is available"
fi

exec $CMD
