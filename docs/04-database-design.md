# 🗄️ DevFlow AI Database Design

## Overview

The DevFlow AI database is designed using a relational database model to support enterprise-level project management, team collaboration, real-time communication, notifications, and AI-powered features.

---

# Entities

1. User
2. Role
3. Organization
4. Team
5. Project
6. Task
7. Comment
8. Attachment
9. Notification
10. Chat Room
11. Message
12. AI History

## User

Purpose:

Stores information about every registered user.

Example Attributes:

- User ID
- Name
- Email
- Password
- Phone
- Profile Picture
- Role
- Created At
- Updated At

## Project

### Purpose

Stores information about projects created within the organization.

### Example Attributes

- Project ID
- Project Name
- Description
- Status
- Priority
- Start Date
- End Date
- Organization ID
- Created By
- Created At
- Updated At

---

## Task

### Purpose

Stores all tasks associated with a project.

### Example Attributes

- Task ID
- Task Name
- Description
- Status
- Priority
- Due Date
- Project ID
- Assigned To (User ID)
- Created By
- Estimated Hours
- Created At
- Updated At

---

## Team

### Purpose

Stores information about teams working on projects.

### Example Attributes

- Team ID
- Team Name
- Description
- Organization ID
- Team Lead (User ID)
- Created At
- Updated At

---

## Message

### Purpose

Stores chat messages exchanged between team members.

### Example Attributes

- Message ID
- Chat Room ID
- Sender ID
- Message Content
- Attachment URL
- Message Type
- Sent At
- Read Status

---

## Notification

### Purpose

Stores notifications sent to users regarding project activities and system updates.

### Example Attributes

- Notification ID
- User ID
- Title
- Message
- Notification Type
- Is Read
- Created At

---

## Organization

### Purpose

Represents a company or workspace where multiple users collaborate on projects.

### Example Attributes

- Organization ID
- Organization Name
- Description
- Owner ID
- Email
- Phone Number
- Address
- Website
- Created At
- Updated At

---

## Role

### Purpose

Defines the permissions and responsibilities assigned to users.

### Example Attributes

- Role ID
- Role Name
- Description
- Created At
- Updated At

---

## AI History

### Purpose

Stores all AI interactions performed by users for future reference and analytics.

### Example Attributes

- AI History ID
- User ID
- Prompt
- AI Response
- AI Model
- Request Type
- Created At

## Relationships

Organization
├── Users

Organization
├── Projects

Project
├── Tasks

Task
├── Comments

Task
├── Attachments

Project
├── Teams

Team
├── Users

Chat Room
├── Messages

User
├── Notifications

User
├── AI History

# Entity Relationships

| Parent Entity | Relationship | Child Entity | Explanation |
|---------------|-------------|--------------|-------------|
| Organization | One-to-Many | Users | One organization contains many users. |
| Organization | One-to-Many | Projects | One organization manages multiple projects. |
| Project | One-to-Many | Tasks | A project consists of multiple tasks. |
| Project | One-to-Many | Teams | Multiple teams can work on a project. |
| Task | One-to-Many | Comments | Each task can have multiple comments. |
| Task | One-to-Many | Attachments | A task can have multiple attachments. |
| User | One-to-Many | Notifications | A user receives many notifications. |
| Chat Room | One-to-Many | Messages | A chat room contains multiple messages. |
| User | One-to-Many | AI History | A user can make many AI requests. |
| User | Many-to-Many | Teams | Users can belong to multiple teams. |
| Users | Many-to-Many | Projects | Users can work on multiple projects, and projects can have multiple users. |

                    Organization
                    /          \
                   /            \
                  ▼              ▼
              Users         Projects
                │                │
                │                ▼
                │             Tasks
                │             /   \
                │            ▼     ▼
                │      Comments  Attachments
                │
                ▼
         Notifications

Users ◄────────────► Teams
Many-to-Many

Projects ◄────────► Users
Many-to-Many

Chat Room
│
▼
Messages

Users
│
▼
AI History      


## Users Table

### Purpose

Stores information about registered users.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| first_name | VARCHAR(100) | NOT NULL | First name |
| last_name | VARCHAR(100) | NOT NULL | Last name |
| email | VARCHAR(255) | UNIQUE, NOT NULL | User email |
| password | VARCHAR(255) | NOT NULL | Encrypted password |
| phone | VARCHAR(20) | | Contact number |
| profile_picture | TEXT | | Image URL |
| role_id | UUID | FK | User role |
| organization_id | UUID | FK | Organization |
| status | VARCHAR(20) | DEFAULT 'ACTIVE' | User status |
| created_at | TIMESTAMP | NOT NULL | Creation time |
| updated_at | TIMESTAMP | NOT NULL | Last update |

## Roles Table

### Purpose

Stores system roles.

| Column | Data Type | Constraints |
|----------|-----------|-------------|
| id | UUID | PK |
| role_name | VARCHAR(50) | UNIQUE |
| description | TEXT | |
| created_at | TIMESTAMP | |
| updated_at | TIMESTAMP | |

## Organizations Table

### Purpose

Stores company/workspace information.

| Column | Data Type | Constraints |
|----------|-----------|-------------|
| id | UUID | PK |
| name | VARCHAR(255) | NOT NULL |
| email | VARCHAR(255) | |
| phone | VARCHAR(20) | |
| address | TEXT | |
| website | VARCHAR(255) | |
| owner_id | UUID | FK |
| created_at | TIMESTAMP | |
| updated_at | TIMESTAMP | |

## Projects Table

| Column | Data Type | Constraints |
|----------|-----------|-------------|
| id | UUID | PK |
| project_name | VARCHAR(255) | NOT NULL |
| description | TEXT | |
| status | VARCHAR(30) | |
| priority | VARCHAR(20) | |
| start_date | DATE | |
| end_date | DATE | |
| organization_id | UUID | FK |
| created_by | UUID | FK |
| created_at | TIMESTAMP | |
| updated_at | TIMESTAMP | |

## Tasks Table

| Column | Data Type | Constraints |
|----------|-----------|-------------|
| id | UUID | PK |
| task_name | VARCHAR(255) | NOT NULL |
| description | TEXT | |
| status | VARCHAR(30) | |
| priority | VARCHAR(20) | |
| due_date | DATE | |
| project_id | UUID | FK |
| assigned_to | UUID | FK |
| created_by | UUID | FK |
| estimated_hours | INTEGER | |
| created_at | TIMESTAMP | |
| updated_at | TIMESTAMP | |

# Additional Database Tables

---

# Team Table

## Purpose

Stores information about teams within an organization.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| name | VARCHAR(255) | NOT NULL | Team name |
| description | TEXT | | Team description |
| organization_id | UUID | Foreign Key | Associated organization |
| team_lead_id | UUID | Foreign Key | Team leader |
| created_at | TIMESTAMP | NOT NULL | Creation time |
| updated_at | TIMESTAMP | NOT NULL | Last update |

---

# Team Members Table

## Purpose

Acts as a junction table between Users and Teams.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| team_id | UUID | Foreign Key | Team reference |
| user_id | UUID | Foreign Key | User reference |
| joined_at | TIMESTAMP | NOT NULL | Joining date |

---

# Project Members Table

## Purpose

Acts as a junction table between Users and Projects.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| project_id | UUID | Foreign Key | Project reference |
| user_id | UUID | Foreign Key | User reference |
| role | VARCHAR(100) | NOT NULL | Role within project |
| joined_at | TIMESTAMP | NOT NULL | Joining date |

---

# Comments Table

## Purpose

Stores comments added to tasks.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| task_id | UUID | Foreign Key | Task reference |
| user_id | UUID | Foreign Key | Comment author |
| content | TEXT | NOT NULL | Comment content |
| created_at | TIMESTAMP | NOT NULL | Creation time |
| updated_at | TIMESTAMP | NOT NULL | Last update |

---

# Attachments Table

## Purpose

Stores files uploaded for tasks.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| task_id | UUID | Foreign Key | Related task |
| uploaded_by | UUID | Foreign Key | User who uploaded |
| file_name | VARCHAR(255) | NOT NULL | File name |
| file_url | TEXT | NOT NULL | Storage URL |
| file_size | BIGINT | | File size in bytes |
| created_at | TIMESTAMP | NOT NULL | Upload time |

---

# Chat Rooms Table

## Purpose

Stores chat rooms for projects or teams.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| project_id | UUID | Foreign Key | Associated project |
| name | VARCHAR(255) | NOT NULL | Chat room name |
| created_at | TIMESTAMP | NOT NULL | Creation time |

---

# Messages Table

## Purpose

Stores messages exchanged inside chat rooms.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| chat_room_id | UUID | Foreign Key | Chat room reference |
| sender_id | UUID | Foreign Key | Sender |
| message | TEXT | NOT NULL | Message content |
| attachment_url | TEXT | | Attachment URL |
| is_read | BOOLEAN | DEFAULT FALSE | Read status |
| created_at | TIMESTAMP | NOT NULL | Sent time |

---

# Notifications Table

## Purpose

Stores notifications sent to users.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| user_id | UUID | Foreign Key | Recipient |
| title | VARCHAR(255) | NOT NULL | Notification title |
| message | TEXT | NOT NULL | Notification message |
| type | VARCHAR(100) | | Notification type |
| is_read | BOOLEAN | DEFAULT FALSE | Read status |
| created_at | TIMESTAMP | NOT NULL | Creation time |

---

# AI History Table

## Purpose

Stores AI prompts and generated responses.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| user_id | UUID | Foreign Key | User reference |
| prompt | TEXT | NOT NULL | User prompt |
| response | TEXT | NOT NULL | AI response |
| model | VARCHAR(100) | | AI model used |
| created_at | TIMESTAMP | NOT NULL | Request time |

---

# Audit Logs Table

## Purpose

Stores system activity logs for security and monitoring.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| user_id | UUID | Foreign Key | User performing action |
| action | VARCHAR(255) | NOT NULL | Action performed |
| entity | VARCHAR(100) | NOT NULL | Entity affected |
| entity_id | UUID | | Related entity |
| ip_address | VARCHAR(45) | | Client IP address |
| created_at | TIMESTAMP | NOT NULL | Action timestamp |

---

# User Sessions Table

## Purpose

Stores active user login sessions.

| Column | Data Type | Constraints | Description |
|----------|-----------|-------------|-------------|
| id | UUID | Primary Key | Unique identifier |
| user_id | UUID | Foreign Key | Logged-in user |
| refresh_token | TEXT | NOT NULL | Refresh token |
| device | VARCHAR(255) | | Device information |
| login_time | TIMESTAMP | NOT NULL | Login time |
| expires_at | TIMESTAMP | NOT NULL | Expiration time |