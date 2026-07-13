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