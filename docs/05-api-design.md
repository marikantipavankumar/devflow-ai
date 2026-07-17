# Authentication APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /auth/register | Register a new user |
| POST | /auth/login | Login user |
| POST | /auth/logout | Logout user |
| POST | /auth/forgot-password | Send password reset link |
| POST | /auth/reset-password | Reset password |
| GET | /auth/verify-email | Verify email address |
| POST | /auth/refresh-token | Generate new access token |

# User APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /users | Get all users |
| GET | /users/{id} | Get user by ID |
| PUT | /users/{id} | Update user |
| DELETE | /users/{id} | Delete user |
| GET | /users/profile | Get logged-in user profile |


# Project APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /projects | Create project |
| GET | /projects | Get all projects |
| GET | /projects/{id} | Get project by ID |
| PUT | /projects/{id} | Update project |
| DELETE | /projects/{id} | Delete project |
| GET | /projects/search | Search projects |

# Task APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /tasks | Create task |
| GET | /tasks | Get all tasks |
| GET | /tasks/{id} | Get task by ID |
| PUT | /tasks/{id} | Update task |
| DELETE | /tasks/{id} | Delete task |
| PUT | /tasks/{id}/status | Update task status |
| POST | /tasks/{id}/assign | Assign task |

# Team APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /teams | Create team |
| GET | /teams | Get all teams |
| GET | /teams/{id} | Get team by ID |
| PUT | /teams/{id} | Update team |
| DELETE | /teams/{id} | Delete team |
| POST | /teams/{id}/members | Add member |
| DELETE | /teams/{id}/members/{userId} | Remove member |

# Notification APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /notifications | Get notifications |
| PUT | /notifications/{id}/read | Mark as read |
| DELETE | /notifications/{id} | Delete notification |

# Chat APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /chat/rooms | Get chat rooms |
| POST | /chat/rooms | Create chat room |
| GET | /chat/rooms/{id}/messages | Get messages |
| POST | /chat/rooms/{id}/messages | Send message |

# AI APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /ai/summarize | Summarize tasks |
| POST | /ai/report | Generate report |
| POST | /ai/sprint-plan | Generate sprint plan |
| POST | /ai/productivity | Productivity suggestions |

# Admin APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /admin/users | Manage users |
| GET | /admin/roles | Manage roles |
| GET | /admin/organizations | Manage organizations |
| GET | /admin/audit-logs | View audit logs |