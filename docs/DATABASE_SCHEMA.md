# Database Schema

## Tables

### users
- `id` (BIGINT, PRIMARY KEY)
- `email` (VARCHAR, UNIQUE, NOT NULL)
- `password` (VARCHAR, NOT NULL)
- `first_name` (VARCHAR, NOT NULL)
- `last_name` (VARCHAR, NOT NULL)
- `date_of_birth` (DATE)
- `age` (INTEGER)
- `height` (DOUBLE)
- `weight` (DOUBLE)
- `average_cycle_length` (INTEGER)
- `average_period_length` (INTEGER)
- `last_period_start` (DATE)
- `activity_level` (VARCHAR)
- `diet_type` (VARCHAR)
- `consent_given` (BOOLEAN)
- `consent_date` (TIMESTAMP)
- `data_sharing_enabled` (BOOLEAN)
- `anonymous_mode` (BOOLEAN)
- `role` (VARCHAR, DEFAULT 'USER')
- `email_verified` (BOOLEAN)
- `email_verification_token` (VARCHAR)
- `email_verification_expiry` (TIMESTAMP)
- `active` (BOOLEAN, DEFAULT true)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### periods
- `id` (BIGINT, PRIMARY KEY)
- `user_id` (BIGINT, FOREIGN KEY -> users.id)
- `start_date` (DATE, NOT NULL)
- `end_date` (DATE)
- `average_flow_intensity` (VARCHAR)
- `pain_level` (INTEGER)
- `notes` (TEXT)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### cycles
- `id` (BIGINT, PRIMARY KEY)
- `user_id` (BIGINT, FOREIGN KEY -> users.id)
- `cycle_start_date` (DATE, NOT NULL)
- `cycle_end_date` (DATE)
- `cycle_length` (INTEGER)
- `period_length` (INTEGER)
- `predicted_period_start` (DATE)
- `predicted_ovulation_date` (DATE)
- `fertile_window_start` (DATE)
- `fertile_window_end` (DATE)
- `is_irregular` (BOOLEAN)
- `prediction_confidence` (DOUBLE)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### symptoms
- `id` (BIGINT, PRIMARY KEY)
- `user_id` (BIGINT, FOREIGN KEY -> users.id)
- `date` (DATE, NOT NULL)
- `symptom_type` (VARCHAR, NOT NULL)
- `severity` (INTEGER)
- `notes` (TEXT)
- `created_at` (TIMESTAMP)

### moods
- `id` (BIGINT, PRIMARY KEY)
- `user_id` (BIGINT, FOREIGN KEY -> users.id)
- `date` (DATE, NOT NULL)
- `mood_type` (VARCHAR, NOT NULL)
- `intensity` (INTEGER)
- `notes` (TEXT)
- `created_at` (TIMESTAMP)

### wellness_logs
- `id` (BIGINT, PRIMARY KEY)
- `user_id` (BIGINT, FOREIGN KEY -> users.id)
- `date` (DATE, NOT NULL)
- `water_intake` (INTEGER)
- `sleep_hours` (INTEGER)
- `sleep_quality` (INTEGER)
- `exercise_minutes` (INTEGER)
- `exercise_type` (VARCHAR)
- `notes` (TEXT)
- `created_at` (TIMESTAMP)

### notifications
- `id` (BIGINT, PRIMARY KEY)
- `user_id` (BIGINT, FOREIGN KEY -> users.id)
- `title` (VARCHAR, NOT NULL)
- `message` (TEXT)
- `type` (VARCHAR)
- `read` (BOOLEAN, DEFAULT false)
- `scheduled_time` (TIMESTAMP)
- `created_at` (TIMESTAMP)

### otp_tokens
- `id` (BIGINT, PRIMARY KEY)
- `email` (VARCHAR, UNIQUE, NOT NULL)
- `otp` (VARCHAR, NOT NULL)
- `type` (VARCHAR)
- `expiry_time` (TIMESTAMP, NOT NULL)
- `used` (BOOLEAN, DEFAULT false)

### user_health_conditions
- `user_id` (BIGINT, FOREIGN KEY -> users.id)
- `condition` (VARCHAR)

## Indexes
- `users.email` (UNIQUE)
- `periods.user_id`
- `periods.start_date`
- `cycles.user_id`
- `symptoms.user_id`
- `moods.user_id`
- `wellness_logs.user_id`

