package model;

public class MonthlyExpenses {
    public String month;
    private float income, expenses;

    public MonthlyExpenses(){

    }

    public MonthlyExpenses(String month, float income, float expenses){
        this.month = month;
        this.income = income;
        this.expenses = expenses;
    }
    public String getMonth(){
        return this.month;
    }
    public float getExpenses(){
        return this.expenses;
    }
    public float getIncome(){
        return this.income;
    }
}
