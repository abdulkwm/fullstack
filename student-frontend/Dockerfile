# Stage 1: Build the React app with Vite
FROM node:alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Use Nginx for serving the built React app
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]