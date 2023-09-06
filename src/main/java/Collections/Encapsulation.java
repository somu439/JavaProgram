package Collections;

class Human{
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
//        public int getAge(){
//            return age;
//        }
//        public void show(){
//            System.out.println("name: "+name+" | age: "+age);
//        }
//
//        public void setAge(int i) {
//            age=i;
//        }
    }
    public class Encapsulation {
    public static void main(String[] args) {

        Human obj = new Human();
        obj.setAge(20);
//        int age= obj.getAge();
        System.out.println(obj.getAge());
//        obj.show();

    }
}
