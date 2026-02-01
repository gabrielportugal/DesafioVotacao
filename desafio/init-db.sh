#!/bin/sh
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE IF NOT EXISTS votacao;
    CREATE DATABASE IF NOT EXISTS "votacao-teste";
EOSQL