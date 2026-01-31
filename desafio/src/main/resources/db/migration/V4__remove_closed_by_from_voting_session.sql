-- Migration para remover a coluna closed_by da tabela voting_session
ALTER TABLE voting_session DROP COLUMN IF EXISTS vse_closed_by;